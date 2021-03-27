package DBLink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Division;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Manages SQL queries for division related data.
 */
public class DivisionLink {

    public static ObservableList<Division> getAllDivisions() {
        /**
         * Gets all Division objects from the database and stores them in the Division model's appointment list.
         * @return Returns an ObservableList of Division objects.
         */
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
               Division division = new Division(divisionID, divisionName, countryID);
                divisionList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionList;
    }
}
