package org.loanManagement.util;


public class DBPropertyUtil {

    // Hardcoding the database connection properties
    private static final String DB_URL = "jdbc:mysql://localhost:3306/loanDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    // Method to get the connection string directly without reading from a properties file
    public static String getConnString() {
        // Create the connection string using hardcoded values
        return DB_URL + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD;
    }

    // Uncomment to test the connection string
    
//    public static void main(String[] args) {
//        System.out.println(getConnString());
//    }
    
}
