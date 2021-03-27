package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the main report screen and associated routing to different report views.
 */
public class ReportsController implements Initializable {


    /**
     * No initialization required for this controller.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Transitions to the main appointments view.
     * @param event Occurs when the back button is clicked.
     * @throws IOException Thrown if the FXML Loader fails.
     */
    @FXML
    void onClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1600, 1000);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Transitions to the contact schedule report view.
     * @param event Occurs when the contact view report button is clicked.
     * @throws IOException Thrown if the FXML Loader fails.
     */
    @FXML
    void onClickViewContact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ContactScheduleReport.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 689);
        stage.setTitle("Type & Month Count Report");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Transitions to the number appointment report view.
     * @param event Occurs when the number appointment report button is clicked.
     * @throws IOException Thrown if the FXML Loader fails.
     */
    @FXML
    void onClickViewType(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/NumberAppointmentTypeReport.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 779, 689);
        stage.setTitle("Type & Month Count Report");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Transitions to the division count report view.
     * @param event Occurs when the division count report button is clicked.
     * @throws IOException Thrown if the FXML Loader fails.
     */
    @FXML
    void onClickViewDivision(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/DivisionCountReport.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 689, 549);
        stage.setTitle("Type & Month Count Report");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
