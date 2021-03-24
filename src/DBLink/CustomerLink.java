package DBLink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Customer;
import models.Division;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                Division division = Division.getDivisionByID(divisionID);
                Country country = Country.getCountryByID(Country.getCountryIDByDivisionID(divisionID));
                Customer customer = new Customer(customerID, name, address, postalCode, phoneNumber, divisionID, country, division);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
