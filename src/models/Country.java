package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int countryID;
    private String countryName;

    public static ObservableList<Country> countriesList = FXCollections.observableArrayList();

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getId() {
        return countryID;
    }

    public String getName() {
        return countryName;
    }
}
