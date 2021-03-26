package DBLink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;
import models.NumberAppointmentTypeReport;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;

public class ReportsLink {

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
}
