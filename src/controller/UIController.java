package controller;

import entity.Application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UIController {
    static Connection conn = DatabaseConnection.getConn();


    //部分未完成，具体看方法内部注释
    void returnItem(Application application){
        try{
            String sql="update application_record set returned = true, return_time = ? where application_id = ?;";
            PreparedStatement stmt= conn.prepareStatement(sql);

            //如何将第一个位置的参数设成年月日形式的时间？想不到的话就要考虑改变时间表达方式了
            stmt.setString(1, String.valueOf(System.currentTimeMillis()));//这里是有问题的

            stmt.setLong(2, application.getAid());
            stmt.executeQuery();

            //通知OverallControl对Item数量进行改变。想要直接在此方法内实现也行，但架构可能稍微没那么好？
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //登出功能。会不会和login放一起更合适？
    void logout(){

    }
}