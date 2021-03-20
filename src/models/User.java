package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    public static ObservableList<User> usersList = FXCollections.observableArrayList();

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
