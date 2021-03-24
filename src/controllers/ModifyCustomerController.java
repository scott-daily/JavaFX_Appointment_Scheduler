package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Country;
import models.Customer;
import models.Division;
import utils.ControlData;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

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
    }



    @FXML
    void onClickCancel(ActionEvent event) {

    }

    @FXML
    void onClickUpdate(ActionEvent event) {

    }

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
