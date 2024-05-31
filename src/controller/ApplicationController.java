package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Application;

public class ApplicationController {
    static Connection conn=DatabaseConnection.getConn();


    /**
     * 查询申请记录
     */
    public static Application[] queryA(){
        try{
            String sql="select * from application_record;";
            PreparedStatement stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index=0;
            Application[]applications=new Application[10];
            while (rs.next()){
                Application application=new Application();
                application.setId(rs.getLong(1));
                application.setAccount(rs.getLong(2));
                application.setId(rs.getLong(3));
                application.setA_name(rs.getString(4));
                application.setM_name(rs.getString(5));
                application.setReturnable(rs.getBoolean(6));
                application.setM_type(rs.getString(7));
                application.setApplication_time(rs.getTimestamp(8));
                application.setReturned(rs.getBoolean(9));
                application.setQuantity(rs.getInt(11));
                applications[index]=application;
                index++;
            }
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 按关键字查询申请记录
     */
    public static Application[] queryA_key(){

        return null;
    }

    /**
     * 创建申请
     */


    /**
     * 查询历史申请记录
     */

    /**
     * 查询历史审核记录
     */


}
