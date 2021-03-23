package controllers;

import DBLink.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;
import utils.ControlData;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label loginTitle;

    @FXML
    private Label regionLabel;

    @FXML
    private Button signinButton;

    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            rb = ResourceBundle.getBundle("utils/Properties", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                loginTitle.setText(rb.getString("loginViewLabel"));
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    loginTitle.setLayoutX(135.0);
                    signinButton.setLayoutX(300.0);
                }
                username.setText(rb.getString("userName"));
                password.setText(rb.getString("password"));
                signinButton.setText(rb.getString("login"));
                exitButton.setText(rb.getString("exit"));
            }

        } catch (MissingResourceException e) {
            e.printStackTrace();
        }

        ZoneId zoneId = ZoneId.systemDefault();
        regionLabel.setText(zoneId.toString());
    }

    @FXML
    public void onClickLogin(ActionEvent actionEvent) throws IOException {

        User.usersList.addAll(UsersLink.getAllUsers());
        Contact.contactsList.addAll(ContactLink.getAllContacts());
        Appointment.appointmentsList.addAll(AppointmentsLink.getAllAppointments());
        Customer.customersList.addAll(CustomerLink.getAllCustomers());

        for (User user : User.usersList) {
            if (user.getUserName().equals(username.getText())) {
                if (user.getUserPassword().equals(password.getText())) {
                    ControlData.setCurrentUser(user);
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                    String logEntry = "User " + user.getUserName() + " successfully logged in at " + utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";
                    writer.write(logEntry);
                    writer.write('\n');
                    writer.close();

                    /*
                    LocalDateTime startTime = ControlData.timeStringToDateTime("2021-03-18 05:52:00");
                    LocalDateTime endTime = ControlData.timeStringToDateTime("2021-03-17 01:25:00");
                    LocalDateTime createdTime = ControlData.timeStringToDateTime("2021-03-17 11:55:00");

                    Appointment test = new Appointment(7, "Dye", "Pams", "Detroit", "Color", Timestamp.valueOf(startTime), Timestamp.valueOf(endTime),
                            Timestamp.valueOf(LocalDateTime.now()), "Scott", Timestamp.valueOf(createdTime), "Camilla", 1, 1, 3, Contact.getContactByID(3));

                    AppointmentsLink.addAppointment(test);
                    //appointmentList.add(test);*/

                    Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1500, 1000);
                    stage.setTitle("Appointments");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                    String logEntry = "User " + user.getUserName() + " provided an invalid password at " + utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";
                    writer.write(logEntry);
                    writer.write('\n');
                    writer.close();
                }
            }
        }

        /*
        for (Appointment appointment : appointmentList) {
            if (appointment.getUserID() == ControlData.getCurrentUser().getUserId()) {
                LocalDateTime startTimeLocal = appointment.getStart().toLocalDateTime();
                LocalDateTime timeAus = ControlData.localToAus(startTimeLocal);
                LocalDateTime timeUTC = ControlData.localToUTC(startTimeLocal);
                LocalDateTime timeEST = ControlData.localToEST(startTimeLocal);
                System.out.println("local date time from getStart(): " + startTimeLocal);
                System.out.println("Time in UTC: " + timeUTC);
                System.out.println("Time in Australia/Sydney Time: " + timeAus);
                System.out.println("Time in EST: " + timeEST);
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(startTimeLocal, now);
                System.out.println("Time now is: " + now);
                System.out.println("Duration between: " + duration.toMinutes());
            }
        }*/
    }

    @FXML
    public void onClickExit() {
        System.out.println("Clicked exit");
    }
}