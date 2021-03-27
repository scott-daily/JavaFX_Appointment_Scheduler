package controllers;

import DBLink.CustomerLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Country;
import models.Customer;
import models.Division;
import utils.ControlData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/**
 * ModifyAppointmentController controls the modify customer view and related functions.
 */
public class ModifyCustomerController implements Initializable {
    /**
     * Field for customer ID.
     */
    @FXML
    private TextField idField;
    /**
     * Field for customer name.
     */
    @FXML
    private TextField nameField;
    /**
     * Field for customer address.
     */
    @FXML
    private TextField addressField;
    /**
     * Field for customer postal code.
     */
    @FXML
    private TextField postalField;
    /**
     * Field for customer phone number.
     */
    @FXML
    private TextField phoneField;
    /**
     * ComboBox for customer country.
     */
    @FXML
    private ComboBox<Country> countryCombo;
    /**
     * ComboBox for customer first level division.
     */
    @FXML
    private ComboBox<Division> divisionCombo;
    @FXML
    private DatePicker endDatePick;
    /**
     * Loads the selected customers data into the associated fields.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        Customer selectedCustomer = ControlData.selectedCustomer;

        // Filter divisionCombo box based on selectedCustomer's country field
        ObservableList<Division> divisionsToShow = FXCollections.observableArrayList();

        countryCombo.setItems(Country.countriesList);
        countryCombo.setValue(selectedCustomer.getCountryObject());

        for (Division division : Division.divisionsList) {
            if (selectedCustomer.getCountryID() == division.getCountryID()) {
                divisionsToShow.add(division);
            }
        }

        divisionCombo.setItems(divisionsToShow);
        divisionCombo.setValue(selectedCustomer.getDivisionObject());

        // Set text fields
        idField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameField.setText(selectedCustomer.getCustomerName());
        addressField.setText(selectedCustomer.getAddress());
        postalField.setText(selectedCustomer.getPostalCode());
        phoneField.setText(selectedCustomer.getPhoneNumber());

    }
    /**
     * Loads the main appointments view.
     * @param event Occurs when cancel button is clicked.
     * @throws IOException Thrown when FXML Loader fails.
     */
    @FXML
    void onClickCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene CustomerViewScene = new Scene(root, 1263, 725);
        stage.setTitle("Customers");
        stage.setScene(CustomerViewScene);
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Updates the stored customer within the database with the form fields after performing validation checks.
     * @param event Occurs when the update button is clicked.
     */
    @FXML
    void onClickUpdate(ActionEvent event) throws SQLException, IOException {
        if (countryCombo.getValue() != null && divisionCombo.getValue() != null && nameField.getText() != null && addressField.getText() != null && postalField.getText() != null && phoneField.getText() != null) {
            String division = divisionCombo.getValue().getDivision();

            Customer updatedCustomer = new Customer(ControlData.selectedCustomer.getCustomerID(), nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(), Division.getDivisionIDByName(division), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), countryCombo.getValue(), divisionCombo.getValue());

            Customer.customersList.set(ControlData.selectedCustomerIndex, updatedCustomer);
            CustomerLink.updateCustomer(updatedCustomer);
            Customer.refreshCustomers();

            Parent root = FXMLLoader.load(getClass().getResource("/views/Customers.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene CustomerViewScene = new Scene(root, 1263, 725);
            stage.setTitle("Customers");
            stage.setScene(CustomerViewScene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    /**
     * Changes the first level division when a user changes the selected country combo box value.
     * @param event Occurs when a new country is selected.
     */
    @FXML
    public void onSelectChangeDiv(ActionEvent event){
        Country selectedCountry = countryCombo.getValue();

        ObservableList<Division> countryDivs = FXCollections.observableArrayList();
        for (Division div : Division.divisionsList) {
            if (div.getCountryID() == selectedCountry.getId()) {
                countryDivs.add(div);
            }
        }
        divisionCombo.setItems(countryDivs);
    }
}
