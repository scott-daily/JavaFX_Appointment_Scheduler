package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class used to model an Appointment object.
 */
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    public static ObservableList<User> usersList = FXCollections.observableArrayList();

    /**
     * Constructor to instantiate a new User object.
     * @param userId The ID of the user.
     * @param userName The name of the user.
     * @param userPassword The password of the user.
     */
    public User(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String toString() {
        return String.valueOf(userId);
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
