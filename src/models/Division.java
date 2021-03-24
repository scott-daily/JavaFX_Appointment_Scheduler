package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Division {
    private int divisionID;
    private StringProperty division;
    private int countryID;

    public static ObservableList<Division> divisionsList = FXCollections.observableArrayList();

    /*public String toString() {
        return division;
    }*/

    public StringProperty divisionProperty() {
        return division;
    }

    public final String getDivision() {
        return divisionProperty().get();
    }

    public final void setDivision(String division) {
        divisionProperty().set(division);
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

    public Division(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = new SimpleStringProperty(division);
        this.countryID = countryID;
    }

    public static Division getDivisionByID(int divisionID) {
        for (Division division : Division.divisionsList) {
            if (division.getDivisionID() == divisionID) {
                return division;
            }
        }
        return null;
    }
}
