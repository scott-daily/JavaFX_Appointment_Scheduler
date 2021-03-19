package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
    private int contactID;
    private String name;
    private String email;
    public static ObservableList<Contact> contactsList = FXCollections.observableArrayList();

    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    public static Contact getContactByID(int contactID) {
        for (Contact contact : contactsList) {
            if (contact.getContactID() == contactID) {
                return contact;
            }
        }
        return null;
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
