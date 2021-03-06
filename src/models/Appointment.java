package models;

import DBLink.AppointmentsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;

/**
 * Class used to model an Appointment object.
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createBy;
    private Timestamp lastUpdate;
    private String updateBy;
    private int customerID;
    private int userID;
    private int contactID;
    private Contact contact;
    /**
     * ObservableList used to store a master list of all Appointment objects.
     */
    public static ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

    /**
     * Constructor to instantiate an Appointment object.
     * @param appointmentID The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param start The appointment start time and date.
     * @param end The appointment end time and date.
     * @param createDate The appointment creation date.
     * @param createBy Which user made the appointment.
     * @param lastUpdate When was last updated the appointment.
     * @param updateBy Who last updated the appointment.
     * @param customerID The customer ID associated with the appointment.
     * @param userID The user ID associated with the appointment.
     * @param contactID The contact ID associated with the appointment.
     * @param contact The Contact object associated with the appointment.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String createBy, Timestamp lastUpdate, String updateBy, int customerID, int userID, int contactID, Contact contact) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Method to pull all Appointment objects from the database to the Appointment model's ObservableList.
     */
    public static void refreshAppointments() {
        appointmentsList = AppointmentsLink.getAllAppointments();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContact() { return contact.getName(); }

    public void setContact (Contact contact) {
        this.contact = contact;
    }

    public Contact getContactObject() {
        return contact;
    }

    /**
     * Method to return a Customer object by inputting it's associated ID number.
     * @param customerID The ID of the customer to search for.
     * @return The Customer object matching the given customer ID.
     */
    public Customer getCustomerByID(int customerID) {
        for (Customer customer : Customer.customersList) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        return null;
    }
    /**
     * Method to return a User object by inputting it's associated ID number.
     * @param userID The ID of the User to search for.
     * @return The User object matching the given User ID.
     */
    public User getUserByID(int userID) {
        for (User user : User.usersList) {
            if (user.getUserId() == userID) {
                return user;
            }
        }
        return null;
    }
}


