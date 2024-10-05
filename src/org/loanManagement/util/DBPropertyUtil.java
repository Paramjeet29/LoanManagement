package org.loanManagement.util;


public class DBPropertyUtil {

    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/loanDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static String getConnString() {

        return DB_URL + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD;
    }

   
    
//    public static void main(String[] args) {
//        System.out.println(getConnString());
//    }
    
}
