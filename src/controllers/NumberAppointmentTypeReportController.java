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
/**
 * Controls the number appointment type schedule report screen and loads data into the tableview.
 */
public class NumberAppointmentTypeReportController implements Initializable {
    /**
     * TableView to hold all NumberAppointmentTypeReport models data.
     */
    @FXML
    private TableView<NumberAppointmentTypeReport> reportTable;
    /**
     * TableColumn to hold an appointment type.
     */
    @FXML
    private TableColumn<NumberAppointmentTypeReport, String> typeCol;
    /**
     * TableColumn to hold a month.
     */
    @FXML
    private TableColumn<NumberAppointmentTypeReport, String> monthCol;
    /**
     * TableColumn to hold the count.
     */
    @FXML
    private TableColumn<NumberAppointmentTypeReport, Integer> countCol;

    /**
     * Loads the Report TableView with all NumberAppointmentType models.
     * @param location The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
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
    /**
     * Goes back to the main report screen.
     * @param event Occurs when the back button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
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
