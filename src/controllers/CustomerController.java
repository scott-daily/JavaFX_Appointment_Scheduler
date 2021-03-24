package controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import models.Country;
import models.Customer;
import models.Division;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<Division> divisionCombo;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> firstLevelCol;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        countryCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCountry()));
        firstLevelCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDivision()));

        custTableView.setItems(Customer.customersList);
    }

    @FXML
    void onClickCancel(ActionEvent event) {

    }

    @FXML
    void onClickSave(ActionEvent event) {

    }
}
