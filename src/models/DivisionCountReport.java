package models;

import DBLink.ReportsLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class DivisionCountReport {
    private String division;
    private int divisionCount;
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

    public DivisionCountReport(String division, int divisionCount) {
        this.division = division;
        this.divisionCount = divisionCount;
    }

    public static void refreshDivisionCount() throws SQLException {
        divisionCountList = ReportsLink.getDivisionCountReport();
    }
}
