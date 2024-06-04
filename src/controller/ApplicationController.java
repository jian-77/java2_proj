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
            String sql = "select * from application_record a join item i on i.id = a.item_id join users u on u.account = a.user_account\n" +
                    "join item_quantity on i.id = item_quantity.id where status=0 order by  application_id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Application[] applications = new Application[1000];
            while (rs.next()) {
                Application application = new Application();
                application.setAid(rs.getLong(1));
                application.getUser().setAccount(rs.getLong(2));
                application.getItem().setId(rs.getLong(3));
                application.setApplication_time(rs.getString(4));
                application.setReturned(rs.getBoolean(5));
                application.setReturn_time(rs.getString(6));
                application.setApplied_quantity(rs.getInt(7));
                application.setStatus(rs.getInt(8));
                application.getManager().setAccount(rs.getLong(10));
                application.getItem().setName(rs.getString(11));
                application.getItem().setType(rs.getString(12));
                application.getItem().setReturnable(rs.getBoolean(13));
                application.getItem().setQuantity(rs.getInt(20));
                application.getItem().setDeleted(rs.getBoolean(14));
                application.getUser().setName(rs.getString(17));

                applications[index] = application;
                index++;
            }
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Application[] query_record(long account) {
        try {
            String sql = "select * from application_record a join item i on i.id = a.item_id join users u on u.account = a.user_account join item_quantity on i.id = item_quantity.id " +
                    "where status!=0 and manager_account=? order by  application_id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,account);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Application[] applications = new Application[1000];
            while (rs.next()) {
                Application application = new Application();
                application.setAid(rs.getLong(1));
                application.getUser().setAccount(rs.getLong(2));
                application.getItem().setId(rs.getLong(3));
                application.setApplication_time(rs.getString(4));
                application.setReturned(rs.getBoolean(5));
                application.setReturn_time(rs.getString(6));
                application.setApplied_quantity(rs.getInt(7));
                application.setStatus(rs.getInt(8));
                application.getManager().setAccount(rs.getLong(10));
                application.getItem().setName(rs.getString(11));
                application.getItem().setType(rs.getString(12));
                application.getItem().setReturnable(rs.getBoolean(13));
                application.getItem().setQuantity(rs.getInt(20));
                application.getUser().setName(rs.getString(17));

                applications[index] = application;
                index++;
            }
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Application[] query_record_U(long account) {
        try {
            String sql = "select * from application_record a join item i on i.id = a.item_id join users u on u.account = a.user_account join item_quantity on i.id = item_quantity.id left join users m on m.account=manager_account " +
                    "where user_account=?  order by  application_id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1,account);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Application[] applications = new Application[1000];
            while (rs.next()) {
                Application application = new Application();
                application.setAid(rs.getLong(1));
                application.getUser().setAccount(rs.getLong(2));
                application.getItem().setId(rs.getLong(3));
                application.setApplication_time(rs.getString(4));
                application.setReturned(rs.getBoolean(5));
                application.setReturn_time(rs.getString(6));
                application.setApplied_quantity(rs.getInt(7));
                application.setStatus(rs.getInt(8));
                application.getManager().setAccount(rs.getLong(10));
                application.getItem().setName(rs.getString(11));
                application.getItem().setType(rs.getString(12));
                application.getItem().setReturnable(rs.getBoolean(13));
                application.getItem().setQuantity(rs.getInt(20));
                application.getUser().setName(rs.getString(17));
                application.getManager().setAccount(rs.getLong(21));
                application.getManager().setPassword(rs.getString(22));
                application.getManager().setName(rs.getString(23));
                applications[index] = application;
                index++;
            }
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * 按关键字查询申请记录
//     */
//    //我不知道具体是什么关键字，所以就按照根据条件查询来写了
//    public static Application[] queryA_key(String condition) {
//        try {
//            String sql = "select * from application_record join item i on i.id = application_record.item_id " +
//                    "join users u on u.account = application_record.user_account " +
//                    "join users v on v.account = application_record.manager_account;" +
//                    "where ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, condition);
//            ResultSet rs = stmt.executeQuery();
//            int index = 0;
//            Application[] applications = new Application[10];
//            while (rs.next()) {
//                Application application = new Application();
//                application.setAid(rs.getLong(1));
//                application.setApplication_time(rs.getString(4));
//                application.setReturned(rs.getBoolean(5));
//                application.setReturn_time(rs.getString(6));
//                application.setApplied_quantity(rs.getInt(7));
//                User user = new User();
//                Item item = new Item();
//                User manager = new User();
//                user.setAccount(rs.getLong("account"));
//                user.setPassword(rs.getString("password"));
//                user.setName(rs.getString("u.name"));
//                user.setPrivilege(rs.getBoolean("privilege"));
//                application.setUser(user);
//                application.setManager(manager);
//                item.setId(rs.getLong("i.id"));
//                item.setName(rs.getString("i.name"));
//                item.setType(rs.getString("type"));
//                item.setReturnable(rs.getBoolean("returnable"));
//                item.setQuantity(rs.getInt("quantity"));
//                application.setItem(item);
//                applications[index] = application;
//                index++;
//            }
//            return applications;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 创建申请
     */
    public static void createApplication(Application application){
        try {
            String sql = "insert into application_record(user_account, item_id, " +
                    "application_time, return_time, applied_quantity,status) " +
                    "values (?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, application.getUser().getAccount());
            stmt.setLong(2, application.getItem().getId());
            stmt.setString(3, application.getApplication_time());
            stmt.setString(4, application.getReturn_time());
            stmt.setInt(5,application.getApplied_quantity());
            stmt.setInt(6,0);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pass(Application application,long account){
        try {
            String sql = "UPDATE application_record SET status = 1 , returned=false, " +
                    "manager_account=?  where application_id= ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, account);
            stmt.setLong(2, application.getAid());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reject(Application application,long account){
        try {
            String sql = "UPDATE application_record SET status = 2 , returned=false," +
                    "manager_account=?  where application_id= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, account);
            stmt.setLong(2, application.getAid());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void returned(Application application,String return_time){
        try {
            String sql = "UPDATE application_record SET returned = true,return_time=? where application_id= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,return_time);
            stmt.setLong(2, application.getAid());
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


