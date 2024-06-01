package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Application;

public class ApplicationController {
    static Connection conn = DatabaseConnection.getConn();


    /**
     * 查询申请记录
     */
    public static Application[] queryA() {
        try {
            String sql = "select * from application_record;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Application[] applications = new Application[10];
            while (rs.next()) {
                Application application = new Application();
                application.setAid(rs.getLong(1));
                application.user.setAccount(rs.getLong(2));
                application.user.setName(rs.getString(3));
                application.item.setName(rs.getString(4));
                application.item.setReturnable(rs.getBoolean(5));
                application.item.setType(rs.getString(6));
                application.setApplication_time(rs.getString(7));
                application.setReturned(rs.getBoolean(8));
                application.setQuantity(rs.getInt(10));
                applications[index] = application;
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
    public static Application[] queryA_key() {

        return null;
    }

    /**
     * 创建申请
     */
    public static void createApplication(Application application){
        try {
            String sql = "insert into application_record(account, applicant_name, material_name, returnable, material_type, returned, applied_quantity,manager_name,application_time) " +
                    "values (?,?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,application.user.getAccount());
            stmt.setString(2, application.user.getName());
            stmt.setString(3, application.item.getName());
            stmt.setBoolean(4, application.item.isReturnable());
            stmt.setString(5,application.item.getType());
            stmt.setBoolean(6,false);
            stmt.setInt(7,application.getQuantity());
            stmt.setString(8,application.getManager());
            stmt.setString(9,application.getApplication_time());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}



    /**
     * 查询历史申请记录
     */

    /**
     * 查询历史审核记录
     */


