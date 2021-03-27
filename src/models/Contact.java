package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class used to model a Contact object.
 */
public class Contact {
    private int contactID;
    private String name;
    private String email;
    /**
     * ObservableList used to store a master list of all Contact objects.
     */
    public static ObservableList<Contact> contactsList = FXCollections.observableArrayList();

    /**
     * Constructor method to instantiate a new Contact object.
     * @param contactID The contact ID number.
     * @param name The contact name.
     * @param email The contact email.
     */
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * Method to return a Contact object by inputting it's associated ID.
     * @param contactID The ID number of the contact to search for.
     * @return The Contact object with a matching ID number.
     */
    public static Contact getContactByID(int contactID) {
        for (Contact contact : contactsList) {
            if (contact.getContactID() == contactID) {
                return contact;
            }
        }
        return null;
    }

    public String toString() {
        return name;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
