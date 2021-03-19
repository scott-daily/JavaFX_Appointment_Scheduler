package controllers;

import DBLink.AppointmentsLink;
import javafx.beans.Observable;
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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

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
    private ComboBox<Appointment> contactBox;

    @FXML
    private ComboBox<LocalTime> startTimeBox;

    @FXML
    private ComboBox<LocalTime> endTimeBox;

    @FXML
    private RadioButton viewMonthRadio;

    @FXML
    private RadioButton viewWeekRadio;

    @FXML
    private ComboBox<?> custIdBox;

    @FXML
    private ComboBox<?> userIdBox;

    @FXML
    private RadioButton viewAllRadio;

    @FXML
    private DatePicker startDatePick;

    @FXML
    private DatePicker endDatePick;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        //ObservableList<Appointment> allAppointments = AppointmentsLink.getAllAppointments();
        apptTable.setItems(Appointment.appointmentsList);
    }

    @FXML
    void onClickModifyAppt(ActionEvent event) {

    }

    @FXML
    void onClickRemoveAppt(ActionEvent event) throws SQLException {
        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
            AppointmentsLink.deleteAppointment(selectedAppt);
            Appointment.appointmentsList.remove(selectedAppt);
        }
    }

    @FXML
    void onClickSaveAppt(ActionEvent event) {

    }

    @FXML
    void onClickViewCust(ActionEvent event) {

    }
}
