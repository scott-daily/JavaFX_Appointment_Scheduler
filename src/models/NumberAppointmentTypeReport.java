package models;

import DBLink.ReportsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * Class to model a NumberAppointmentTypeReport report.
 */
public class NumberAppointmentTypeReport {
    private String month;
    private String type;
    private int count;
    /**
     * Master list to store all NumberAppointmentTypeReport objects.
     */
    public static ObservableList<NumberAppointmentTypeReport> numApptTypeList = FXCollections.observableArrayList();

    /**
     * Constructor to instantiate a new NumberAppointmentTypeReport object.
     * @param month The month of the appointment.
     * @param type The type of appointment.
     * @param count The count of the month and type.
     */
    public NumberAppointmentTypeReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Method to get all NumberAppointmentTypeReport from the database and store in the numApptTypeList.
     * @throws SQLException
     */
    public static void refreshNumApptList() throws SQLException {
        numApptTypeList = ReportsLink.getNumberApptReport();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
