package controllers;

import DBLink.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import utils.ControlData;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /**
     * TextField to hold a username.
     */
    @FXML
    private TextField username;
    /**
     * TextField to hold a user password.
     */
    @FXML
    private TextField password;
    /**
     * Label for login.
     */
    @FXML
    private Label loginTitle;
    /**
     * Label to display region information.
     */
    @FXML
    private Label regionLabel;
    /**
     * Button to sign in.
     */
    @FXML
    private Button signinButton;
    /**
     * Button to exit the program.
     */
    @FXML
    private Button exitButton;

    /**
     * Loads the resource bundle to enable the login view to display in English or French based on a system's locale.  Detects
     * the user's time zone information and displays it in the regionLabel.
     * @param url
     * @param rb
     */
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

    /**
     * Pulls data out of database into model list to be accessed later. Stores login attempts into a text file to examine later.
     * @param actionEvent Occurs when login button is clicked.
     * @throws IOException Thrown if FXML Loader fails.
     */
    @FXML
    public void onClickLogin(ActionEvent actionEvent) throws IOException {

        ControlData.newLogin = true;
        ControlData.newLoginNoAppt = true;

        User.usersList.addAll(UsersLink.getAllUsers());
        Contact.contactsList.addAll(ContactLink.getAllContacts());
        Appointment.appointmentsList.addAll(AppointmentsLink.getAllAppointments());
        Division.divisionsList.addAll(DivisionLink.getAllDivisions());
        Country.countriesList.addAll(CountriesLink.getAllCountries());
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

                    // Load Appointments view

                    Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1600, 1000);
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