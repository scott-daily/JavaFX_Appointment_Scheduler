package utils;

import models.Appointment;
import models.Customer;
import models.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ControlData {

    private static User currentUser;

    public static Appointment selectedAppointment;
    public static int selectedAppointmentIndex;
    public static int appointmentID;

    public static Customer selectedCustomer;
    public static int selectedCustomerIndex;
    public static int selectedCustomerCountryID;

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

    public static String dateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(dateTime);
    }

    public static LocalDateTime localToEST(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

    public static LocalDateTime localToCST(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/Chicago")).toLocalDateTime();
    }

    public static LocalDateTime utcToLocal(LocalDateTime utcTime) {
        return utcTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

    public static LocalDateTime localToUTC(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime localToAus(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Australia/Sydney")).toLocalDateTime();
    }
}
