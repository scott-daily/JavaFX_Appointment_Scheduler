package utils;

import models.Appointment;
import models.Customer;
import models.User;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ControlData {
    /**
     * User object to store the currently logged in user.
     */
    private static User currentUser;
    /**
     * Appointment object to save the currently selected appointment for modification.
     */
    public static Appointment selectedAppointment;
    /**
     * Variable to store the selected appointments index.
     */
    public static int selectedAppointmentIndex;
    /**
     * Variable to store the selected appointment's ID.
     */
    public static int appointmentID;
    /**
     * Variable to store the selected Customer object.
     */
    public static Customer selectedCustomer;
    /**
     * Used to store the selected customers index.
     */
    public static int selectedCustomerIndex;
    /**
     * Used to indicate whether a visit to the Appointment view is the first or not.
     */
    public static Boolean newLogin = false;
    /**
     * Used to indicate whether or not a new login has an upcoming appointment.
     */
    public static Boolean newLoginNoAppt = false;

    /**
     * Used to set the current user that is logged in.
     * @param user The User object to set it to.
     */
    public static void setCurrentUser(User user) {
        ControlData.currentUser= user;
    }

    /**
     * Used to retrieve the current logged in User object.
     * @return The User object.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Used to convert a LocalDateTime in a system default time into the time in the Eastern time zone.
     * @param localTime The local time in the user time zone.
     * @return A LocalDateTime object representing the time in the user's time zone in EST.
     */
    public static LocalDateTime localToEST(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }
}
