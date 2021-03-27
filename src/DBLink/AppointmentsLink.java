package DBLink;

import models.Appointment;
import models.Contact;
import utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *  Manages SQL queries for appointment related data.
 */
public class AppointmentsLink {
    /**
     * Gets all appointment objects from the database and stores them in the Appointment model's appointment list.
     * @return Returns an ObservableList of Appointment objects.
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String updateBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Contact contact = Contact.getContactByID(contactID);
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createBy, lastUpdate, updateBy, customerID, userID, contactID, contact);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Adds a new appointment to the appointments table witin the database.
     * @param appointment The appointment to add to the database.
     */
    public static void addAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {

            ps.setInt(1,appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setTimestamp(6, appointment.getStart());
            ps.setTimestamp(7, appointment.getEnd());
            ps.setTimestamp(8, appointment.getCreateDate());
            ps.setString(9,appointment.getCreateBy());
            ps.setTimestamp(10, appointment.getLastUpdate());
            ps.setString(11, appointment.getUpdateBy());
            ps.setInt(12,appointment.getCustomerID());
            ps.setInt(13, appointment.getUserID());
            ps.setInt(14, appointment.getContactID());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given appointment from the database.
     * @param appointment The appointment to delete.
     * @throws SQLException Thrown if the appointment doesn't exist.
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {

        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = + " + appointment.getAppointmentID() + ";";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the selected appointment.
     * @param appointment The appointment to update.
     * @throws SQLException Thrown if there is an SQL error.
     */
    public static void updateAppointment(Appointment appointment) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, appointment.getStart());
            ps.setTimestamp(6, appointment.getEnd());
            ps.setTimestamp(7, appointment.getLastUpdate());
            ps.setString(8, appointment.getUpdateBy());
            ps.setInt(9,appointment.getCustomerID());
            ps.setInt(10, appointment.getUserID());
            ps.setInt(11, appointment.getContactID());
            ps.setInt(12, appointment.getAppointmentID());

            ps.executeUpdate();
            System.out.println("Finished updating appointment");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
