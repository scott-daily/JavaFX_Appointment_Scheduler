package models;

import DBLink.ReportsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class NumberAppointmentTypeReport {
    private String month;
    private String type;
    private int count;
    public static ObservableList<NumberAppointmentTypeReport> numApptTypeList = FXCollections.observableArrayList();

    public NumberAppointmentTypeReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

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
