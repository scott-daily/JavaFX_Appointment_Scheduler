package controllers;

import DBLink.AppointmentsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.User;
import utils.ControlData;
import utils.ValidationChecks;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
/**
 * ModifyAppointmentController controls the modify appointment view and related functions.
 */
public class ModifyAppointmentController implements Initializable {
    /**
     * Field for appointment ID.
     */
    @FXML
    private TextField idField;
    /**
     * Field for appointment title.
     */
    @FXML
    private TextField titleField;
    /**
     * Field for appointment description.
     */
    @FXML
    private TextField descriptionField;
    /**
     * Field for appointment location.
     */
    @FXML
    private TextField locationField;
    /**
     * Field for appointment type.
     */
    @FXML
    private TextField typeField;
    /**
     * ComboBox for appointment contact.
     */
    @FXML
    private ComboBox<Contact> contactBox;
    /**
     * ComboBox for appointment start time.
     */
    @FXML
    private ComboBox<LocalTime> startTimeBox;
    /**
     * ComboBox for appointment end time.
     */
    @FXML
    private ComboBox<LocalTime> endTimeBox;
    /**
     * ComboBox for customer ID.
     */
    @FXML
    private ComboBox<Customer> custIdBox;
    /**
     * ComboBox for user ID.
     */
    @FXML
    private ComboBox<User> userIdBox;
    /**
     * DatePicker for appointment start date.
     */
    @FXML
    private DatePicker startDatePick;
    /**
     * DatePicker for appointment end date.
     */
    @FXML
    private DatePicker endDatePick;
    /**
     * Loads the selected appointments data into the associated fields.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

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

        try {
            Appointment selectedAppt = ControlData.selectedAppointment;

            idField.setText(String.valueOf(selectedAppt.getAppointmentID()));
            titleField.setText(selectedAppt.getTitle());
            descriptionField.setText(selectedAppt.getDescription());
            locationField.setText(selectedAppt.getLocation());
            typeField.setText(selectedAppt.getType());
            contactBox.setValue(selectedAppt.getContactObject());
            custIdBox.setValue(selectedAppt.getCustomerByID(selectedAppt.getCustomerID()));
            userIdBox.setValue(selectedAppt.getUserByID(selectedAppt.getUserID()));
            startDatePick.setValue(selectedAppt.getStart().toLocalDateTime().toLocalDate());
            startTimeBox.getSelectionModel().select(selectedAppt.getStart().toLocalDateTime().toLocalTime());
            endDatePick.setValue(selectedAppt.getEnd().toLocalDateTime().toLocalDate());
            endTimeBox.getSelectionModel().select(selectedAppt.getEnd().toLocalDateTime().toLocalTime());

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setContentText("An appointment must be selected for modification.");
            alert.showAndWait();
        }
    }

    /**
     * Updates the stored appointment within the database with the form fields after performing validation checks.
     * @param event Occurs when the update button is clicked.
     */
    @FXML
    void onClickUpdate(ActionEvent event) {
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

                try {
                    Appointment updatedAppt = new Appointment(ControlData.selectedAppointment.getAppointmentID(), titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime),
                            Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), custIdBox.getValue().getCustomerID(), userIdBox.getValue().getUserId(), contactBox.getValue().getContactID(), Contact.getContactByID(contactBox.getValue().getContactID()));

                    Appointment.appointmentsList.set(ControlData.selectedAppointmentIndex, updatedAppt);
                    AppointmentsLink.updateAppointment(updatedAppt);
                    Appointment.refreshAppointments();

                    Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1600, 1000);
                    stage.setTitle("Appointments");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads the main appointments view.
     * @param event Occurs when cancel button is clicked.
     * @throws IOException Thrown when FXML Loader fails.
     */
    @FXML
    void onClickCancel (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1600, 1000);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
