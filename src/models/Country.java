package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int countryID;
    private StringProperty countryName;

    public static ObservableList<Country> countriesList = FXCollections.observableArrayList();

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = new SimpleStringProperty(countryName);
    }

    public StringProperty countryNameProperty() {
        return countryName;
    }

    public final String getCountryName() {
        return countryNameProperty().get();
    }

    public final void setCountryName(String countryName) {
        countryNameProperty().set(countryName);
    }

    public static Country getCountryByID(int countryID) {
        for (Country country : Country.countriesList) {
            if (country.countryID == countryID) {
                return country;
            }
        }
        return null;
    }

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

    /*public String getName() {
        return countryName;
    }*/
}
