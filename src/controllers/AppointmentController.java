package controllers;

import DBLink.AppointmentsLink;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.User;
import utils.ControlData;
import utils.ValidationChecks;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    }

    @FXML
    void onClickModifyAppt(ActionEvent event) {

    }
    /**
     * Stores used Part ID's so that new Parts have unique ID's.
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
            AppointmentsLink.deleteAppointment(selectedAppt);
            Appointment.appointmentsList.remove(selectedAppt);
            Appointment.refreshAppointments();
            apptTable.setItems(Appointment.appointmentsList);
        }
    }

    @FXML
    void onClickSaveAppt(ActionEvent event) {
        LocalTime startTime = startTimeBox.getValue();
        LocalTime endTime = endTimeBox.getValue();
        LocalDate startDate = startDatePick.getValue();
        LocalDate endDate = endDatePick.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        int customerID = custIdBox.getValue().getId();

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
                    Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), Timestamp.valueOf(LocalDateTime.now()), "null", custIdBox.getValue().getId(), userIdBox.getValue().getUserId(), contactBox.getValue().getContactID(), Contact.getContactByID(contactBox.getValue().getContactID()));

        AppointmentsLink.addAppointment(newAppt);
        Appointment.appointmentsList.add(newAppt);
        Appointment.refreshAppointments();

        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        typeField.clear();
        contactBox.getSelectionModel().clearSelection();
        contactBox.setPromptText("Contact Name");
        custIdBox.getSelectionModel().clearSelection();
        custIdBox.setPromptText("Customer ID");
        userIdBox.getSelectionModel().clearSelection();
        userIdBox.setPromptText("User ID");
        startDatePick.getEditor().clear();
        startDatePick.setPromptText("Start Date");
        endDatePick.getEditor().clear();
        endDatePick.setPromptText("End Date");
        startTimeBox.getSelectionModel().clearSelection();
        startTimeBox.setPromptText("Start Time");
        endTimeBox.getSelectionModel().clearSelection();
        endTimeBox.setPromptText("End Time");
        }
    }

    @FXML
    void onClickViewCust(ActionEvent event) {

    }
}
