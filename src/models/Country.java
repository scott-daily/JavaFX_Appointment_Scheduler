package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class used to model a Country object.
 */
public class Country {

    private int countryID;
    private StringProperty countryName;
    /**
     * ObservableList used to store a master list of all Country objects.
     */
    public static ObservableList<Country> countriesList = FXCollections.observableArrayList();

    /**
     * Constructor to instantiate a new Country object.
     * @param countryID The country ID.
     * @param countryName The country name.
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = new SimpleStringProperty(countryName);
    }

    /**
     * Returns a StringProperty object to enable the ObservableList to function correctly so that the country name appears
     * as expected in the Customer TableView.
     * @return A StringProperty object with the country name.
     */
    public StringProperty countryNameProperty() {
        return countryName;
    }

    /**
     * Returns the String stored within the StringProperty object holding the country name.
     * @return A String representing the country name.
     */
    public final String getCountryName() {
        return countryNameProperty().get();
    }

    /**
     * Method to set the country name.
     * @param countryName The name of the country.
     */
    public final void setCountryName(String countryName) {
        countryNameProperty().set(countryName);
    }

    /**
     * Method to get a Country object by it's associated ID number.
     * @param countryID The ID of the country to find.
     * @return The Country object.
     */
    public static Country getCountryByID(int countryID) {
        for (Country country : Country.countriesList) {
            if (country.countryID == countryID) {
                return country;
            }
        }
        return null;
    }

    /**
     * Method to get a country ID by first getting it's division ID.  This is done so that
     * getCountryByID can then find the actual Country object by the returned country ID.
     * @param divisionID The divsion ID to search for.
     * @return The country ID that is associated with the given division ID.
     */
    public static int getCountryIDByDivisionID(int divisionID) {
        for (Division division : Division.divisionsList) {
            if (division.getDivisionID() == divisionID) {
                return division.getCountryID();
            }
        }
        return -1;
    }

    public int getId() {
        return countryID;
    }

    public String toString() {
        return getCountryName();
    }
}
