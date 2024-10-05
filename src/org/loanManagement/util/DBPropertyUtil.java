//package org.loanManagement.util;
//
//
//public class DBPropertyUtil {
//
//
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/loanDB";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "";
//
//    public static String getConnString() {
//
//        return DB_URL + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD;
//    }
//
//   
//    
////    public static void main(String[] args) {
////        System.out.println(getConnString());
////    }
//    
//}
package org.loanManagement.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnString(String propFileName) {
        String connString = null;
        Properties propsObject = new Properties();
        
        try (FileInputStream fis = new FileInputStream(propFileName)) {
            propsObject.load(fis);
            
            String url = propsObject.getProperty("db.url");
            String username = propsObject.getProperty("db.username");
            String password = propsObject.getProperty("db.password");
            
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException("Database URL is missing or empty in properties file");
            }
            
            connString = url + "?user=" + username + "&password=" + password;
            System.out.println("Connection string created: " + connString);
        } catch (IOException e) {
            System.err.println("Error: Unable to read properties from file: " + propFileName);
            e.printStackTrace();
        }
        
        return connString;
    }
}