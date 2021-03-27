package controllers;

import DBLink.AppointmentsLink;
import DBLink.CustomerLink;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.*;
import utils.ControlData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SplittableRandom;

public class CustomerController implements Initializable {
    /**
     * TextField to hold a customer ID.
     */
    @FXML
    private TextField idField;
    /**
     * TextField to hold a customer name.
     */
    @FXML
    private TextField nameField;
    /**
     * TextField to hold a customer address.
     */
    @FXML
    private TextField addressField;
    /**
     * TextField to hold a customer postal code.
     */
    @FXML
    private TextField postalField;
    /**
     * TextField to hold a customer phone number.
     */
    @FXML
    private TextField phoneField;
    /**
     * ComboBox to hold a country.
     */
    @FXML
    private ComboBox<Country> countryCombo;
    /**
     * ComboBox to hold a first level division.
     */
    @FXML
    private ComboBox<Division> divisionCombo;
    /**
     * TableView to hold customer data.
     */
    @FXML
    private TableView<Customer> custTableView;
    /**
     * TableColumn to hold a customer name.
     */
    @FXML
    private TableColumn<Customer, String> nameCol;
    /**
     * TableColumn to hold a customer address.
     */
    @FXML
    private TableColumn<Customer, String> addressCol;
    /**
     * TableColumn to hold a customer postal code.
     */
    @FXML
    private TableColumn<Customer, String> postalCol;
    /**
     * TableColumn to hold a customer phone number.
     */
    @FXML
    private TableColumn<Customer, String> phoneCol;
    /**
     * TableColumn to hold a customer country.
     */
    @FXML
    private TableColumn<Customer, String> countryCol;
    /**
     * TableColumn to hold a customer first level division.
     */
    @FXML
    private TableColumn<Customer, String> firstLevelCol;
    /**
     * TableColumn to hold a customer ID.
     */
    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    /**
     * Initializes the Customer Controller. Lambda used as a parameter to the setCellFactoryValue method,
     * it iterates over each value and adds a read only wrapper to each returned value.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
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

        countryCombo.setItems(Country.countriesList);
        divisionCombo.setItems(Division.divisionsList);
    }

    /**
     * Goes back to the main report screen.
     * @param event Occurs when the cancel button is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    void onClickCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1600, 1000);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
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

    /**
     * Stores used Appointment ID's so that new Appointments have unique ID's.
     */
    private ArrayList<Integer> usedIdArray = new ArrayList<Integer>();

    /**
     * Generates a unique ID to be used in a Part constructor method.
     * Uses the SplittableRandom class to generate a unique sequence of values between the specified bounds.
     * Uses boolean value isUnique to track if the generated ID already exists within the usedIdArray.
     * @return Returns an int that represents a unique ID between 1 and 1000.
     */
    private int generateUniqueID() {
        boolean isUnique = false;
        int randomID = new SplittableRandom().nextInt(1, 1_001);

        while (!isUnique) {
            if (!usedIdArray.contains(randomID)) {
                isUnique = true;
                usedIdArray.add(randomID);
            }
            else {
                randomID = new SplittableRandom().nextInt(1, 1_001);
            }
        }
        return randomID;
    }

    /**
     * Saves the customer that is in the form after checking to ensure fields are correctly filled out.
     * @param event Occurs when save button is clicked.
     */
    @FXML
    void onClickSave(ActionEvent event) {
        if (countryCombo.getValue() != null && divisionCombo.getValue() != null && nameField.getText() != null && addressField.getText() != null && postalField.getText() != null && phoneField.getText() != null) {
            String division = divisionCombo.getValue().getDivision();

            Customer customer = new Customer(generateUniqueID(), nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(), Division.getDivisionIDByName(division), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), Timestamp.valueOf(LocalDateTime.now()), ControlData.getCurrentUser().getUserName(), countryCombo.getValue(), divisionCombo.getValue());
            CustomerLink.addCustomer(customer);
            Customer.customersList.add(customer);
            Customer.refreshCustomers();
            custTableView.setItems(Customer.customersList);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setContentText("Valid values must be entered for all inputs.");
            alert.showAndWait();
        }

        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryCombo.getSelectionModel().clearSelection();
        divisionCombo.getSelectionModel().clearSelection();
        custTableView.refresh();
    }

    /**
     * Removes the customer that is selected after first removing all associated appointments.
     * @param event Occurs when remove button is clicked with a customer selected in the TableView.
     */
    @FXML
    void onClickRemove(ActionEvent event) throws SQLException {
        if (custTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = custTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " + selectedCustomer.getCustomerName() + "? Any associated appointments will also be removed.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setTitle("Customer Removal Warning");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                // Remove customer's appointments before removing the customer.
                for (Appointment appointment : Appointment.appointmentsList) {
                    if (appointment.getCustomerID() == selectedCustomer.getCustomerID()) {
                        AppointmentsLink.deleteAppointment(appointment);
                    }
                }
                CustomerLink.deleteCustomer(selectedCustomer);
                Customer.customersList.remove(selectedCustomer);
                Customer.refreshCustomers();
                custTableView.setItems(Customer.customersList);
            }
        }
    }
    /**
     * Modifies the customer that is selected.
     * @param event Occurs when modify button is clicked with a customer selected in the TableView.
     */
    @FXML
    void onClickModify(ActionEvent event) throws SQLException, IOException {
        if (custTableView.getSelectionModel().getSelectedItem() != null) {
            ControlData.selectedCustomer = custTableView.getSelectionModel().getSelectedItem();
            ControlData.selectedCustomerIndex = custTableView.getSelectionModel().getSelectedIndex();

            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyCustomer.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene ModifyAppointmentScene = new Scene(root, 625, 710);
            stage.setTitle("Modify Appointment");
            stage.setScene(ModifyAppointmentScene);
            stage.centerOnScreen();
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A customer must be selected for modification.");
            alert.showAndWait();
        }
    }
}
