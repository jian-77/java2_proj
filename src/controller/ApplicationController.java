package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Application;
import entity.Item;
import entity.User;

public class ApplicationController {
    static Connection conn = DatabaseConnection.getConn();


    /**
     * 查询申请记录
     */
    public static Application[] queryA() {
        try {
            String sql = "select * from application_record join item i on i.id = application_record.item_id " +
                    "join users u on u.account = application_record.user_account " +
                    "join users v on v.account = application_record.manager_account;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Application[] applications = new Application[10];
            while (rs.next()) {
                Application application = new Application();
                application.setAid(rs.getLong(1));
                application.setApplication_time(rs.getString(4));
                application.setReturned(rs.getBoolean(5));
                application.setReturn_time(rs.getString(6));
                application.setApplied_quantity(rs.getInt(7));
                User user = new User();
                Item item = new Item();
                User manager = new User();
                user.setAccount(rs.getLong(9));
                user.setPassword(rs.getString(10));
                user.setName(rs.getString(11));
                user.setPrivilege(rs.getBoolean(12));
                application.setUser(user);
                manager.setAccount(rs.getLong(13));
                manager.setPassword(rs.getString(14));
                manager.setName(rs.getString(15));
                manager.setPrivilege(rs.getBoolean(16));
                application.setManager(manager);
                item.setId(rs.getLong(17));
                item.setName(rs.getString(18));
                item.setType(rs.getString(19));
                item.setReturnable(rs.getBoolean(20));
                item.setQuantity(rs.getInt(22));
                application.setItem(item);
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
            String sql = "insert into application_record(user_account, item_id, application_time, " +
                    "returned, return_time, applied_quantity, manager_account) " +
                    "values (?,?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, application.getUser().getAccount());
            stmt.setLong(2, application.getItem().getId());
            stmt.setString(3, application.getApplication_time());
            stmt.setBoolean(4,application.isReturned());
            stmt.setString(5, application.getReturn_time());
            stmt.setInt(6,application.getApplied_quantity());
            stmt.setLong(7,application.getManager().getAccount());
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


