package DBLink;

import models.Appointment;
import utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsLink {

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
                LocalDateTime end = rs.getObject("End", LocalDateTime.class);
                LocalDateTime createDate = rs.getObject("Create_Date", LocalDateTime.class);
                String createBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getObject("Last_Update", LocalDateTime.class);
                String updateBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createBy, lastUpdate, updateBy, customerID, userID, contactID);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }
}
