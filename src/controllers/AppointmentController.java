package controllers;

import DBLink.AppointmentsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.SplittableRandom;

public class AppointmentController implements Initializable {

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, Contact> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<Contact> contactBox;

    @FXML
    private ComboBox<LocalTime> startTimeBox;

    @FXML
    private ComboBox<LocalTime> endTimeBox;

    @FXML
    private RadioButton viewMonthRadio;

    @FXML
    private RadioButton viewWeekRadio;

    @FXML
    private ComboBox<Customer> custIdBox;

    @FXML
    private ComboBox<User> userIdBox;

    @FXML
    private RadioButton viewAllRadio;

    @FXML
    private DatePicker startDatePick;

    @FXML
    private DatePicker endDatePick;

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

        // Write code to provide an alert when there is an appointment within 15 minutes of the user’s log-in. A custom message should be displayed
        // in the user interface and include the appointment ID, date, and time. If the user does not have any appointments within 15 minutes of
        // logging in, display a custom message in the user interface indicating there are no upcoming appointments.
        // Note: Since evaluation may be testing your application outside of business hours, your alerts must be robust enough
        // to trigger an appointment within 15 minutes of the local time set on the user’s computer, which may or may not be EST.

        // TEST 15 MINUTE NOTIFICATION BELOW (TEST CODE)
        /*Contact test = new Contact(999, "Arnold", "arnold@test.com");
        Appointment testAppt = new Appointment(999, "Test", "Test", "Test", "Test", Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)), Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)),Timestamp.valueOf(LocalDateTime.now()), "test", Timestamp.valueOf(LocalDateTime.now()), "test", 2, 1, 999, test);
        Appointment.appointmentsList.add(testAppt);*/

        Boolean hasAppointmentSoon = false;

        if (ControlData.newLogin) {
            ControlData.newLogin = false;

            for (Appointment appointment : Appointment.appointmentsList) {
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
     * Stores used Appointment ID's so that new Appointments have unique ID's.
     */
    private ArrayList<Integer> usedIdArray = new ArrayList<>();

    /**
     * Generates a unique ID to be used in a Part constructor method.
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

    @FXML
    void onClickRemoveAppt(ActionEvent event) throws SQLException {
        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setTitle("Appointment Removal");
            alert.setContentText("Appointment with an ID of: " + selectedAppt.getAppointmentID() + ", and type: " + selectedAppt.getType() + " was removed.");
            alert.showAndWait();

            AppointmentsLink.deleteAppointment(selectedAppt);
            Appointment.appointmentsList.remove(selectedAppt);
            Appointment.refreshAppointments();
            apptTable.setItems(Appointment.appointmentsList);
        }
    }

    @FXML
    void onClickSaveAppt(ActionEvent event) throws SQLException {
        apptTable.refresh();
        if (startTimeBox.getValue() != null && endTimeBox.getValue() != null && startDatePick.getValue() != null && endDatePick.getValue() != null && custIdBox.getValue() != null) {
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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setContentText("Valid values must be entered for all inputs.");
            alert.showAndWait();
        }
    }


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
}
