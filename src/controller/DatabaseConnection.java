package controller;

import java.sql.*;
public class DatabaseConnection {
    private static Connection conn=null;
    public static Connection getConn(){
        String url = "jdbc:postgresql://localhost:5432/manage_system";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, "postgres", "2003yhjzsqybh");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed");
        }
        return conn;
    }
}