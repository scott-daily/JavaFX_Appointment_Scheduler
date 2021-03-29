package controllers;

import DBLink.AppointmentsLink;
import DBLink.CustomerLink;
import models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.*;
import utils.ControlData;
import utils.ValidationChecks;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * AppointmentController controls the main screen's appointment view and related functions.
 */
public class AppointmentController implements Initializable {
    /**
     * TableView that holds a view of all Appointments in the database.
     */
    @FXML
    private TableView<Appointment> apptTable;

    /**
     * TableColumn that holds an appointment ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    /**
     * TableColumn that holds an appointment title.
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;

    /**
     * TableColumn that holds an appointment description.
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    /**
     * TableColumn that holds an appointment location.
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;

    /**
     * TableColumn that holds an appointment contact.
     */
    @FXML
    private TableColumn<Appointment, Contact> contactCol;

    /**
     * TableColumn that holds an appointment type.
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;

    /**
     * TableColumn that holds an appointment start time and date.
     */
    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    /**
     * TableColumn that holds an appointment end time and date.
     */
    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    /**
     * TableColumn that holds an appointment's customer ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    /**
     * TextField that holds an appointment ID.
     */
    @FXML
    private TextField idField;

    /**
     * TextField that holds an appointment title.
     */
    @FXML
    private TextField titleField;

    /**
     * TextField that holds an appointment description.
     */
    @FXML
    private TextField descriptionField;

    /**
     * TextField that holds an appointment location.
     */
    @FXML
    private TextField locationField;

    /**
     * TextField that holds an appointment type.
     */
    @FXML
    private TextField typeField;

    /**
     * ComboBox that holds an appointment contact.
     */
    @FXML
    private ComboBox<Contact> contactBox;

    /**
     * ComboBox that holds an appointment start time.
     */
    @FXML
    private ComboBox<LocalTime> startTimeBox;

    /**
     * ComboBox that holds an appointment end time.
     */
    @FXML
    private ComboBox<LocalTime> endTimeBox;

    /**
     * RadioButton that shows monthly appointments.
     */
    @FXML
    private RadioButton viewMonthRadio;

    /**
     * RadioButton that shows weekly appointments.
     */
    @FXML
    private RadioButton viewWeekRadio;

    /**
     * ComboBox that holds a customer ID.
     */
    @FXML
    private ComboBox<Customer> custIdBox;

    /**
     * ComboBox that holds a user ID.
     */
    @FXML
    private ComboBox<User> userIdBox;

    /**
     * RadioButton that shows all appointments.
     */
    @FXML
    private RadioButton viewAllRadio;

    /**
     * DatePicker that holds a start date.
     */
    @FXML
    private DatePicker startDatePick;

    /**
     * DatePicker that holds a end date.
     */
    @FXML
    private DatePicker endDatePick;
    /**
     * Tracks whether user has an appointment starting soon.
     */
    Boolean hasAppointmentSoon = false;

    /**
     * Loads the Appointment TableView with all appointments in the database and initializes form controls.  Checks to
     * see if there are any appointments starting within 15 minutes of the associated User ID that just logged in.
     * Lambda method checks if any appointment with the associated User ID has a starting time within 15 minutes of login.  This is used
     * to clean up the method and make the code more readable.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        // Appointment TableView set up
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        Appointment.refreshAppointments();
        apptTable.setItems(Appointment.appointmentsList);

        // ComboBox set up

        contactBox.setItems(Contact.contactsList);
        custIdBox.setItems(Customer.customersList);
        userIdBox.setItems(User.usersList);

        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
        LocalTime begin = LocalTime.MIDNIGHT;
        timeList.add(begin);

        int i = 1;
        while (i < 96) {
            timeList.add(begin.plusMinutes(15));
            begin = begin.plusMinutes(15);
            i++;
        }
        startTimeBox.setItems(timeList);
        endTimeBox.setItems(timeList);

        if (ControlData.newLogin) {
            ControlData.newLogin = false;
            Customer.customersList.addAll(CustomerLink.getAllCustomers());
            Appointment.appointmentsList.forEach((appointment) -> nearAppointmentTime(appointment));
        }

        if (!hasAppointmentSoon && ControlData.newLoginNoAppt) {
            ControlData.newLoginNoAppt = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setTitle("Appointment Notification");
            alert.setContentText("There are no appointments for this user starting within 15 minutes.");
            alert.showAndWait();
            hasAppointmentSoon = false;
        }
    }

    /**
     * Checks an appointment with associated user ID to find out if there are any appointments
     * saved that have a starting time within 15 minutes of the user login.
     * @param appointment
     */
    public void nearAppointmentTime(Appointment appointment) {
        if (appointment.getUserID() == ControlData.getCurrentUser().getUserId()) {
            if (appointment.getStart().toLocalDateTime().isAfter(LocalDateTime.now())) {
                Duration duration = Duration.between(LocalDateTime.now(), appointment.getStart().toLocalDateTime());
                if (duration.toMinutes() < 15) {
                    hasAppointmentSoon = true;
                    ControlData.newLoginNoAppt = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setTitle("Appointment Notification");
                    alert.setContentText("Appointment starting within 15 minutes at: " + appointment.getStart() + ", with an appointment ID of: " + appointment.getAppointmentID() + ".");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Transitions to the modify appointment screen to adjust an appointment.
     * @param event Occurs when modify appointment button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickModifyAppt(ActionEvent event) throws IOException {
        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            ControlData.selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
            ControlData.appointmentID = apptTable.getSelectionModel().getSelectedItem().getAppointmentID();
            ControlData.selectedAppointmentIndex = apptTable.getSelectionModel().getSelectedIndex();

            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene ModifyAppointmentScene = new Scene(root, 625, 710);
            stage.setTitle("Modify Appointment");
            stage.setScene(ModifyAppointmentScene);
            stage.centerOnScreen();
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("An appointment must be selected for modification.");
            alert.showAndWait();
        }
    }

    /**
     * Transitions to the report viewing screen.
     * @param event Occurs when view reports button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickViewReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 925, 474);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    /**
     * Stores used Appointment ID's so that new Appointments have unique ID's.
     */
    private ArrayList<Integer> usedIdArray = new ArrayList<Integer>();

    /**
     * Generates a unique ID to be used in the Appointment constructor method.
     * Uses the SplittableRandom class to generate a unique sequence of values between the specified bounds.
     * Uses boolean value isUnique to track if the generated ID already exists within the usedIdArray.
     * @return Returns an int that represents a unique ID between 1 and 1000.
     */
    private int generateUniqueID() {
        boolean isUnique = false;
        int randomID = new SplittableRandom().nextInt(1, 1_001);

        while (!isUnique) {
            if (!usedIdArray.contains(randomID)) {
                isUnique = true;
                usedIdArray.add(randomID);
            }
            else {
                randomID = new SplittableRandom().nextInt(1, 1_001);
            }
        }
        return randomID;
    }

    /**
     * Deletes the selected appointment.
     * @param event Occurs when an appointment is selected and the remove button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickRemoveAppt(ActionEvent event) throws SQLException {
        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected appointment? Appointment with an ID of: " + selectedAppt.getAppointmentID() + ", and type: " + selectedAppt.getType() + " will be removed.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setTitle("Appointment Removal Confirmation");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                AppointmentsLink.deleteAppointment(selectedAppt);
                Appointment.appointmentsList.remove(selectedAppt);
                Appointment.refreshAppointments();
                apptTable.setItems(Appointment.appointmentsList);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("An appointment must be selected to be removed.");
            alert.showAndWait();
        }
    }

    /**
     * Saves the selected appointment.
     * @param event Occurs when an appointment is selected and the save button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickSaveAppt(ActionEvent event) throws SQLException {
        apptTable.refresh();
        if (startTimeBox.getValue() != null && endTimeBox.getValue() != null && startDatePick.getValue() != null && endDatePick.getValue() != null && custIdBox.getValue() != null) {
            if (startTimeBox.getValue().isAfter(endTimeBox.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Time Error");
                alert.setContentText("Start time must be before end time.");
                alert.showAndWait();
            } else if (startTimeBox.getValue().equals(endTimeBox.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Time Error");
                alert.setContentText("Start time cannot be equal to end time.");
                alert.showAndWait();
            } else {
                    LocalTime startTime = startTimeBox.getValue();
                    LocalTime endTime = endTimeBox.getValue();
                    LocalDate startDate = startDatePick.getValue();
                    LocalDate endDate = endDatePick.getValue();
                    LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                    LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
                    int customerID = custIdBox.getValue().getCustomerID();

                    Boolean isSameDate = ValidationChecks.isSameDate(startDate, endDate);
                    Boolean isDuringBusinessHours = ValidationChecks.isDuringBusinessHours(startDateTime, endDateTime);
                    Boolean isNotOverlapping = ValidationChecks.isNotOverlapping(startDateTime, endDateTime, customerID);

                    if (isSameDate) {
                        System.out.println("Appt starts and ends on the same date.");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Appointment Date Error");
                        alert.setContentText("Appointment times must be on the same day.");
                        alert.showAndWait();
                    }
                    if (isDuringBusinessHours) {
                        System.out.println("Submitted appt is during business hours.");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Outside Business Hours");
                        alert.setContentText("Appointments must be between 8:00 AM EST and 10:00 PM EST.");
                        alert.showAndWait();
                    }
                    if (isNotOverlapping) {
                        System.out.println("Submitted appt is not overlapping any existing appointments.");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Overlapping Appointments");
                        alert.setContentText("Customer appointments cannot have overlapping times.");
                        alert.showAndWait();
                    }

                    if (isSameDate && isDuringBusinessHours && isNotOverlapping) {

                        Appointment newAppt = new Appointment(generateUniqueID(), titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime),
                                Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), custIdBox.getValue().getCustomerID(), userIdBox.getValue().getUserId(), contactBox.getValue().getContactID(), Contact.getContactByID(contactBox.getValue().getContactID()));

                        AppointmentsLink.addAppointment(newAppt);
                        Appointment.appointmentsList.add(newAppt);
                        Appointment.refreshAppointments();
                        apptTable.setItems(Appointment.appointmentsList);

                        titleField.clear();
                        descriptionField.clear();
                        locationField.clear();
                        typeField.clear();
                        contactBox.getSelectionModel().clearSelection();
                        custIdBox.getSelectionModel().clearSelection();
                        userIdBox.getSelectionModel().clearSelection();
                        startDatePick.getEditor().clear();
                        startDatePick.setPromptText("Start Date");
                        endDatePick.getEditor().clear();
                        endDatePick.setPromptText("End Date");
                        startTimeBox.getSelectionModel().clearSelection();
                        startTimeBox.setPromptText("Start Time");
                        endTimeBox.getSelectionModel().clearSelection();
                        endTimeBox.setPromptText("End Time");

                        apptTable.refresh();
                    }
                }
            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setContentText("Valid values must be entered for all inputs.");
            alert.showAndWait();
        }
    }

    /**
     * Transitions to the customer view screen.
     * @param event Occurs when the view customers button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickViewCust(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene CustomerViewScene = new Scene(root, 1263, 725);
        stage.setTitle("Customers");
        stage.setScene(CustomerViewScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Reveals all appointments in the database.
     * @param event Occurs when radio button is clicked.
     */
    @FXML
    void onClickViewAll(ActionEvent event) {
        apptTable.setItems(Appointment.appointmentsList);
    }

    /**
     * Displays only appointments in the current month.
     * @param event Occurs when radio button is clicked.
     */
    @FXML
    void onClickViewMonth(ActionEvent event) {
        ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();

        for (Appointment appt : Appointment.appointmentsList) {
            if (appt.getStart().toLocalDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                monthlyAppts.add(appt);
            }
        }

        apptTable.setItems(monthlyAppts);
    }

    /**
     * Displays only appointments in the current week.
     * @param event Occurs when radio button is clicked.
     */
    @FXML
    void onClickViewWeek(ActionEvent event) {

        ObservableList<Appointment> weeklyAppts = FXCollections.observableArrayList();
        int currentWeekOfYear = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());

        for (Appointment appt : Appointment.appointmentsList) {
            if (appt.getStart().toLocalDateTime().toLocalDate().get(WeekFields.of(Locale.getDefault()).weekOfYear()) == currentWeekOfYear) {
                weeklyAppts.add(appt);
            }
        }
        apptTable.setItems(weeklyAppts);
    }

    /**
     * Exits the program when the exit button is clicked.
     * @param event Occurs when the exit button is clicked.
     */
    @FXML
    public void onClickExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> exitAnswer = alert.showAndWait();

        if (exitAnswer.isPresent() && exitAnswer.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
