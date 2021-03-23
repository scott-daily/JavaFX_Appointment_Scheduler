package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.User;
import utils.ControlData;

import java.net.URL;
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
    void onClickUpdate(ActionEvent event) {

    }

    @FXML
    void onClickCancel(ActionEvent event) {

    }
}
