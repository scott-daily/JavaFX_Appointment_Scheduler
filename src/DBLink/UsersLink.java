package DBLink;

import utils.DBConnection;
import models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 *  Manages SQL queries for user related data.
 */
public class UsersLink {
    /**
     * Gets all User objects from the database and stores them in the User model's User object list.
     * @return Returns an ObservableList of User objects.
     */
    public static ObservableList<User> getAllUsers() {

        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userId, userName, password);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}