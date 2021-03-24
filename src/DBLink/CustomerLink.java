package DBLink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Country;
import models.Customer;
import models.Division;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomerLink {

    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Division division = Division.getDivisionByID(divisionID);
                Country country = Country.getCountryByID(Country.getCountryIDByDivisionID(divisionID));
                Customer customer = new Customer(customerID, name, address, postalCode, phoneNumber, divisionID, createDate, createdBy, lastUpdate, lastUpdatedBy, country, division);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public static void addCustomer(Customer customer) {

        String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {

                ps.setInt(1, customer.getCustomerID());
                ps.setString(2, customer.getCustomerName());
                ps.setString(3, customer.getAddress());
                ps.setString(4, customer.getPostalCode());
                ps.setString(5, customer.getPhoneNumber());
                ps.setTimestamp(6, customer.getCreateDate());
                ps.setString(7, customer.getCreatedBy());
                ps.setTimestamp(8, customer.getLastUpdate());
                ps.setString(9, customer.getLastUpdatedBy());
                ps.setInt(10,customer.getDivisionID());

                ps.executeUpdate();
                System.out.println("Added customer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer customer) throws SQLException {

        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = + " + customer.getCustomerID() + ";";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer) throws SQLException {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setTimestamp(5, customer.getLastUpdate());
            ps.setString(6, customer.getLastUpdatedBy());
            ps.setInt(7,customer.getDivisionID());
            ps.setInt(8, customer.getCustomerID());

            ps.executeUpdate();
            System.out.println("Finished updating customer" + customer.getCustomerID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
