package DBLink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ContactScheduleReport;
import models.DivisionCountReport;
import models.NumberAppointmentTypeReport;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
/**
 *  Manages SQL queries for report related data.
 */
public class ReportsLink {
    /**
     * Gets all NumberApptReport object data from the database and stores them in the NumberApptReport model's object list.
     * @return Returns an ObservableList of NumberApptReport objects.
     */
    public static ObservableList<NumberAppointmentTypeReport> getNumberApptReport() throws SQLException {

        ObservableList<NumberAppointmentTypeReport> numberApptReportList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Count(Appointment_ID) AS \"Count\", Type, MONTH(Start) AS \"Month\" FROM appointments GROUP BY Type, Start";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String month = new DateFormatSymbols().getMonths()[rs.getInt("Month")-1];
                String type = rs.getString("Type");
                int count = rs.getInt("Count");

                NumberAppointmentTypeReport report = new NumberAppointmentTypeReport(month, type, count);
                numberApptReportList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberApptReportList;
    }
    /**
     * Gets all ContactScheduleReport object data from the database and stores them in the ContactScheduleReport model's object list.
     * @return Returns an ObservableList of ContactScheduleReport objects.
     */
    public static ObservableList<ContactScheduleReport> getContactScheduleReport() throws SQLException {

        ObservableList<ContactScheduleReport> contactScheduleList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT contacts.Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments JOIN contacts USING(Contact_ID) GROUP BY Contact_ID, Appointment_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contact = rs.getString("Contact_Name");
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");

                ContactScheduleReport report = new ContactScheduleReport(contact, appointmentID, title, type, description, start, end, customerID);
                contactScheduleList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactScheduleList;
    }
    /**
     * Gets all DivisionCountReport object data from the database and stores them in the DivisionCountReport model's object list.
     * @return Returns an ObservableList of DivisionCountReport objects.
     */
    public static ObservableList<DivisionCountReport> getDivisionCountReport() throws SQLException {

        ObservableList<DivisionCountReport> divisionCountList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Division, COUNT(Division) AS \"Division Count\" FROM first_level_divisions join customers on first_level_divisions.Division_ID = customers.Division_ID GROUP BY Division";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String division = rs.getString("Division");
                int divisionCount = rs.getInt("Division Count");

                DivisionCountReport report = new DivisionCountReport(division, divisionCount);
                divisionCountList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionCountList;
    }
}
