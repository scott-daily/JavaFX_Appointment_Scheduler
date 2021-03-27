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
import models.ContactScheduleReport;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controls the contact schedule report screen and loads data into the tableview.
 */
public class ContactScheduleReportController implements Initializable {
    /**
     * Table to hold all ContactScheduleReport objects.
     */
    @FXML
    private TableView<ContactScheduleReport> reportTable;
    /**
     * TableColumn to hold contacts
     */
    @FXML
    private TableColumn<ContactScheduleReport, String> contactCol;
    /**
     * TableColumn to hold appointment ID's.
     */
    @FXML
    private TableColumn<ContactScheduleReport, Integer> appointmentID;
    /**
     * TableColumn to hold appointment title's.
     */
    @FXML
    private TableColumn<ContactScheduleReport, String> title;
    /**
     * TableColumn to hold appointment types;
     */
    @FXML
    private TableColumn<ContactScheduleReport, String> type;
    /**
     * TableColumn to hold appointment descriptions;
     */
    @FXML
    private TableColumn<ContactScheduleReport, String> description;

    /**
     * TableColumn to hold appointment start time and dates;
     */
    @FXML
    private TableColumn<ContactScheduleReport, LocalDateTime> start;
    /**
     * TableColumn to hold appointment end time and dates;
     */
    @FXML
    private TableColumn<ContactScheduleReport, LocalDateTime> end;
    /**
     * TableColumn to hold appointment customer ID's;
     */
    @FXML
    private TableColumn<ContactScheduleReport, Integer> customerID;

    /**
     * Loads the Report TableView with all ContactScheduleReport models.
     * @param location The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ContactScheduleReport.refreshContactSchedule();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        reportTable.setItems(ContactScheduleReport.contactScheduleList);
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
