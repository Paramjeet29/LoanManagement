package org.loanManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    
    public static Connection getConnection() {
        Connection con = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded...");

          
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

   
//    public static void main(String[] args) {
//        Connection con = getConnection();
//        System.out.println(con);
//    }
    
}
