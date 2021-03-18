package utils;

import models.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ControlData {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        ControlData.currentUser= user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static LocalDateTime timeStringToDateTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timeString, formatter);
    }

    public static LocalDateTime localToEST(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/Toronto")).toLocalDateTime();
    }

    public static LocalDateTime utcToLocal(LocalDateTime utcTime) {
        return utcTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/Toronto")).toLocalDateTime();
    }

    public static LocalDateTime localToUTC(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime localToAus(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Australia/Sydney")).toLocalDateTime();
    }
}
