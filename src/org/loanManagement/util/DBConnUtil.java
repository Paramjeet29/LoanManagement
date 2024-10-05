package org.loanManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    // Method to establish a connection using the hardcoded connection string
    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded...");

            // Get the connection string from DBPropertyUtil
            String connString = DBPropertyUtil.getConnString();
            con = DriverManager.getConnection(connString);
            System.out.println("Connected to Database...");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver Loading Failed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to Connect to Database");
        }

        return con;
    }

    // Uncomment to test the connection
    
//    public static void main(String[] args) {
//        Connection con = getConnection();
//        System.out.println(con);
//    }
    
}
