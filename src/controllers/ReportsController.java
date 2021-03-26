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

public class ReportsController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
