package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class used to model a Division object.
 */
public class Division {
    private int divisionID;
    /**
     * Used to store the division name so that the TableView can use it properly.
     */
    private StringProperty division;
    private int countryID;
    /**
     * ObservableList used to store a master list of all Division objects.
     */
    public static ObservableList<Division> divisionsList = FXCollections.observableArrayList();

    public StringProperty divisionProperty() {
        return division;
    }

    public final String getDivision() {
        return divisionProperty().get();
    }

    public final void setDivision(String division) {
        divisionProperty().set(division);
    }

    public String toString() {
        return getDivision();
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Constructor method to instantiate a new Division object.
     * @param divisionID The division ID.
     * @param division The division name.
     * @param countryID The associated country ID.
     */
    public Division(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = new SimpleStringProperty(division);
        this.countryID = countryID;
    }

    /**
     * Method to get Division object by ID.
     * @param divisionID The ID of the division object to search for.
     * @return The Division object with the matching ID.
     */
    public static Division getDivisionByID(int divisionID) {
        for (Division division : Division.divisionsList) {
            if (division.getDivisionID() == divisionID) {
                return division;
            }
        }
        return null;
    }

    /**
     * Method to get a Division ID number by inputting it's name.
     * @param divisionName The name of the division to search for.
     * @return The int ID number of the matching division.
     */
    public static int getDivisionIDByName(String divisionName) {
        for (Division division : Division.divisionsList) {
            if (division.getDivision().equals(divisionName)) {
                return division.getDivisionID();
            }
        }
        return -1;
    }
}
