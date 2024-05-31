package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    static Connection conn=DatabaseConnection.getConn();

    public static int Login(Long account, String password){
        try{
            String sql="select * from users where account=? and password=?;";
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.setLong(1,account);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                 if (rs.getBoolean("privilege")){
                     return 1;
                 }
                 else{
                     return 2;
                 }
            }
            else{
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
