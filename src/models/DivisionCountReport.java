package models;

import DBLink.ReportsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * Class to model a division count report.
 */
public class DivisionCountReport {
    private String division;
    private int divisionCount;
    /**
     * Master list of all DivisionCountReport objects.
     */
    public static ObservableList<DivisionCountReport> divisionCountList = FXCollections.observableArrayList();

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getDivisionCount() {
        return divisionCount;
    }

    public void setDivisionCount(int divisionCount) {
        this.divisionCount = divisionCount;
    }

    /**
     * Constructor method to instantiate a new DivisionCountReport.
     * @param division The name of the division.
     * @param divisionCount The count of the division.
     */
    public DivisionCountReport(String division, int divisionCount) {
        this.division = division;
        this.divisionCount = divisionCount;
    }

    /**
     * Gets all DivisionCountReport objects from the database and stores them in the master list.
     * @throws SQLException
     */
    public static void refreshDivisionCount() throws SQLException {
        divisionCountList = ReportsLink.getDivisionCountReport();
    }
}
