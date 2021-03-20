package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    public static ObservableList<Customer> customersList = FXCollections.observableArrayList();


    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    public String toString() {
        return String.valueOf(customerID);
    }

    public int getId() { return customerID; }

    public String getName() {
        return customerName;
    }

    public String getAddress() { return address; }

    public String getPostalCode() { return postalCode; }

    public String getPhoneNumber() { return phoneNumber; }

    public int getDivisionID() { return divisionID; }
}
