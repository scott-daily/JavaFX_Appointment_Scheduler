package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class used to connect to the MySQl database.
 */
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String url = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08dzx";

    private static final String jbdcURL = protocol + vendor + url + dbName;
    private static final String mySQLJBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "U08dzx";
    private static Connection dbConnection = null;

    /**
     * Method to start a connection to the database.
     * @return
     */
    public static Connection openConnection() {
        try {
            Class.forName(mySQLJBCDriver);
            dbConnection = DriverManager.getConnection(jbdcURL, username, "53689261945");

            System.out.println("Connection successful");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    /**
     * Method that returns a dbConnection object to be used.
     * @return
     */
    public static Connection getConnection() {
        return dbConnection;
    }

    /**
     * Method to close a connection at program exit.
     */
    public static void closeConnection() {
        try {
            dbConnection.close();
        } catch (Exception ignored) {

        }
    }
}
