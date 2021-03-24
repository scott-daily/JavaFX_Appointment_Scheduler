package models;

import DBLink.AppointmentsLink;
import DBLink.CustomerLink;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Country country;
    private Division division;
    public static ObservableList<Customer> customersList = FXCollections.observableArrayList();


    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Country country, Division divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.country = country;
        this.division = divisionName;
    }

    public String toString() {
        return String.valueOf(customerID);
    }

    public static void refreshCustomers() {
        customersList = CustomerLink.getAllCustomers();
    }

    public int getCustomerID() {
        return customerID;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDivision() {
        return division.getDivision();
    }

    public String getCountry() {
        return country.getCountryName();
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public static ObservableList<Customer> getCustomersList() {
        return customersList;
    }

    public static void setCustomersList(ObservableList<Customer> customersList) {
        Customer.customersList = customersList;
    }
}
