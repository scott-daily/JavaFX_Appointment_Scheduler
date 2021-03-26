package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NumberAppointmentTypeReport;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NumberAppointmentTypeReportController implements Initializable {

    @FXML
    private TableView<NumberAppointmentTypeReport> reportTable;

    @FXML
    private TableColumn<NumberAppointmentTypeReport, String> typeCol;

    @FXML
    private TableColumn<NumberAppointmentTypeReport, String> monthCol;

    @FXML
    private TableColumn<NumberAppointmentTypeReport, Integer> countCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            NumberAppointmentTypeReport.refreshNumApptList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        reportTable.setItems(NumberAppointmentTypeReport.numApptTypeList);
    }

    @FXML
    void onClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 925, 474);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
