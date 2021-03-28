package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import java.util.Locale;

/**
 * Main method that starts the JavaFX Application and creates the primary stage.
 */
public class Main extends Application {
    /**
     * Method to start the JavaFX Application and instantiate the primary stage for the program.
     * @param primaryStage The primary Stage object.
     * @throws Exception Throws when the FXML Loader fails.
     */
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

    /**
     * Main method that opens the MySQL database connection.
     * @param args
     */
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
