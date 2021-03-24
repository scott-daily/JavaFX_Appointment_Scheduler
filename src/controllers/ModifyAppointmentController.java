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

public class ModifyAppointmentController implements Initializable {

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
    private ComboBox<Customer> custIdBox;

    @FXML
    private ComboBox<User> userIdBox;

    @FXML
    private DatePicker startDatePick;

    @FXML
    private DatePicker endDatePick;

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

    @FXML
    void onClickUpdate(ActionEvent event) throws SQLException {
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
