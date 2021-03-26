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

public class ContactScheduleReportController implements Initializable {

    @FXML
    private TableView<ContactScheduleReport> reportTable;

    @FXML
    private TableColumn<ContactScheduleReport, String> contactCol;

    @FXML
    private TableColumn<ContactScheduleReport, Integer> appointmentID;

    @FXML
    private TableColumn<ContactScheduleReport, String> title;

    @FXML
    private TableColumn<ContactScheduleReport, String> type;

    @FXML
    private TableColumn<ContactScheduleReport, String> description;

    @FXML
    private TableColumn<ContactScheduleReport, LocalDateTime> start;

    @FXML
    private TableColumn<ContactScheduleReport, LocalDateTime> end;

    @FXML
    private TableColumn<ContactScheduleReport, Integer> customerID;

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
