package utils;

import models.User;

public class ControlData {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        ControlData.currentUser= user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
