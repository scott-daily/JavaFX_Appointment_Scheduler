package models;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;


    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public int getId() { return customerID; }

    public String getName() {
        return customerName;
    }

    public String getAddress() { return address; }

    public String getPostalCode() { return postalCode; }

    public String getPhoneNumber() { return phoneNumber; }
}
