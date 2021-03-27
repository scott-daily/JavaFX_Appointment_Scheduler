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
import models.DivisionCountReport;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controls the division count schedule report screen and loads data into the tableview.
 */
public class DivisionCountReportController implements Initializable {
    /**
     * TableView to hold all DivisionCountReport data.
     */
    @FXML
    private TableView<DivisionCountReport> divCountTable;
    /**
     * TableColumn to hold first level division data.
     */
    @FXML
    private TableColumn<DivisionCountReport, String> division;
    /**
     * TableColumn to hold the division count.
     */
    @FXML
    private TableColumn<DivisionCountReport, Integer> divisionCount;

    /**
     * Loads the Report TableView with all DivisionCountReport models.
     * @param location The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DivisionCountReport.refreshDivisionCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        divisionCount.setCellValueFactory(new PropertyValueFactory<>("divisionCount"));
        divCountTable.setItems(DivisionCountReport.divisionCountList);
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
