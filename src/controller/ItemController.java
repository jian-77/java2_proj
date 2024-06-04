
package controller;
import entity.Application;
import entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController {

    static Connection conn = DatabaseConnection.getConn();

    /**
     * 添加物资类型
     */

    public static void addItemType(Item item) {
        try {
            String sql = "insert into item(name,type,returnable,isDeleted) values (?,?,?,false) returning id";
            String sql2="insert into item_quantity (id, quantity) values (?,0);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getType());
            stmt.setBoolean(3, item.isReturnable());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item.setId(rs.getLong("id"));
            } else {
                    System.out.println("no generate id");
            }
            stmt2.setLong(1,item.getId());
            stmt2.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 删除物资类型
     */


    public static void deleteItemType(Item item){
        try{
            String sql="update item set isDeleted =true where id = ?";
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.setLong(1, item.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 增加物资数量
     */
    //offset为相比原本数量的偏移量，new quantity = old quantity + offset
    //如果传进来的item的quantity已经是新的了，那就不需要offset，直接把第一个'?'设为item.getQuantity就行了

    public static void editItemQuantity(Item item, int number){
        try{
            String sql="update item_quantity set quantity = ? where id = ?";
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.setInt(1, number);
            stmt.setLong(2, item.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







    /**
     * 默认搜索物资
     */
    public static Item[] queryI() {
        try {
            String sql = "select iq.id,name,type,returnable,quantity,isDeleted\n" +
                    "from item join item_quantity iq on item.id = iq.id where isDeleted =false order by id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Item[] items = new Item[1000];
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong(1));
                item.setName(rs.getString(2));
                item.setType(rs.getString(3));
                item.setReturnable(rs.getBoolean(4));
                item.setQuantity(rs.getInt(5));
                item.setDeleted(rs.getBoolean(6));
                items[index] = item;
                index++;
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item[] queryItemQuantity() {
        try {
            String sql = " select item.id,item.name,quantity,type,returnable from item join item_quantity on item.id = item_quantity.id order by id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Item[] items = new Item[1000];
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong(1));
                item.setName(rs.getString(2));
                item.setQuantity(rs.getInt(3));
                item.setType(rs.getString(4));
                item.setReturnable(rs.getBoolean(5));
                items[index] = item;
                index++;
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}



