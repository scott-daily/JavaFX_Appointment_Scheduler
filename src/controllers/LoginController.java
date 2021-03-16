package controllers;

import DBLink.CountriesLink;
import DBLink.UsersLink;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Country;
import models.Main;
import models.User;
import utils.ControlData;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
                regionLabel.setText(rb.getString("regionLabel"));
                username.setText(rb.getString("userName"));
                password.setText(rb.getString("password"));
                signinButton.setText(rb.getString("login"));
                exitButton.setText(rb.getString("exit"));
            }

        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickLogin() throws IOException {
        System.out.println("Clicked login");

        /*ObservableList<Country> countryList= CountriesLink.getAllCountries();

        for (Country country : countryList) {
            System.out.println(country.getName());
        }*/
        ObservableList<User> userList= UsersLink.getAllUsers();

        for (User user : userList) {
            if (user.getUserName().equals(username.getText())) {
                if (user.getUserPassword().equals(password.getText())) {
                    ControlData.setCurrentUser(user);
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                    String logEntry = "User " + user.getUserName() + " successfully logged in at " + utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";
                    writer.write(logEntry);
                    writer.write('\n');
                    writer.close();
                }
                else {
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                    String logEntry = "User " + user.getUserName() + " provided an invalid password at " + utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";
                    writer.write(logEntry);
                    writer.write('\n');
                    writer.close();
                }
            }
        }
        //System.out.println(ControlData.getCurrentUser().getUserId());
    }

    @FXML
    public void onClickExit() {
        System.out.println("Clicked exit");
    }
}
