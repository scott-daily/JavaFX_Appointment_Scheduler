package models;

import DBLink.ReportsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ContactScheduleReport {
    private String contact;
    private int appointmentID;
    private String title;
    private String type;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    public static ObservableList<ContactScheduleReport> contactScheduleList = FXCollections.observableArrayList();

    public ContactScheduleReport(String contact, int appointmentID, String title, String type, String description, Timestamp start, Timestamp end, int customerID) {
        this.contact = contact;
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    public static void refreshContactSchedule() throws SQLException {
        contactScheduleList = ReportsLink.getContactScheduleReport();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
