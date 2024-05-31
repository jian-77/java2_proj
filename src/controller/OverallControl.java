package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OverallControl {
    public static void main(String[] args) {
       try{
           Connection conn= DatabaseConnection.getConn();
           String sql="insert into users(account, password, name, permission) values (111111,12314,'user1',true)";
           PreparedStatement stmt= conn.prepareStatement(sql);
           stmt.executeQuery();

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
