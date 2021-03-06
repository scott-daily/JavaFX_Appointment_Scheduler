package DBLink;

import utils.DBConnection;
import models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 *  Manages SQL queries for country related data.
 */
public class CountriesLink {
    /**
     * Gets all country objects stored in the database.
     * @return An ObservableList of Country objects.
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country country = new Country(countryId, countryName);
                countryList.add(country);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }
}
