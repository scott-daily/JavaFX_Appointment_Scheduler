package models;

import DBLink.CustomerLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
/**
 * Class used to model an Customer object.
 */
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
    /**
     * ObservableList used to store a master list of all Customer objects.
     */
    public static ObservableList<Customer> customersList = FXCollections.observableArrayList();

    /**
     * Constructor to instantiate a new Customer object.
     * @param customerID The customer ID.
     * @param customerName The customer name.
     * @param address The customer address.
     * @param postalCode The customer postal code.
     * @param phoneNumber The customer phone number.
     * @param divisionID The customer's first level division.
     * @param createDate The date the customer was created.
     * @param createdBy The user that created the customer.
     * @param lastUpdate When the customer was last updated.
     * @param lastUpdatedBy Which user last updated the customer.
     * @param country The Country object associated with the customer.
     * @param divisionName The Division object associated with the customer.
     */
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

    /**
     * Used to obtain a String representation of the customer ID that is stored as an int.
     * @return A String representing the customer ID.
     */
    public String toString() {
        return String.valueOf(customerID);
    }

    /**
     * Method to pull all Customer objects from the database to the Customer model's ObservableList.
     */
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

    public Country getCountryObject() {
        return country;
    }

    public Division getDivisionObject() {
        return division;
    }

    public int getCountryID() {
        return country.getId();
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
}
