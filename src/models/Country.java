package models;

public class Country {

    private int countryID;
    private String countryName;

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
