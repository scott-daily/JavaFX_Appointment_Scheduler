package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/Login.fxml"));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle("écran de connexion");
        } else {
            primaryStage.setTitle("Login Screen");
        }
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));  // <--- Way to test different Locale in program
        DBConnection.openConnection();
        Application.launch(args);
        try {
            DBConnection.closeConnection();
        } catch (Exception ignored) {

        }
    }
}
