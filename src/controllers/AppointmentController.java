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

import java.net.URL;
import java.sql.SQLException;
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
            apptTable.refresh();
        }
    }

    @FXML
    void onClickSaveAppt(ActionEvent event) {

    }

    @FXML
    void onClickViewCust(ActionEvent event) {

    }
}
