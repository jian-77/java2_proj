
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

    public void addItemType(Item item) {
        try {
            String sql = "insert into item(name,type,returnable) values (?,?,?) returning id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getType());
            stmt.setBoolean(3, item.isReturnable());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                item.setId(rs.getLong("id"));
            } else {
                System.out.println("no generate id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除物资类型
     */
    public void deleteItemType(Item item) {
        try {
            String sql = "delete from item where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, item.getId());
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 增加物资数量
     */
    //offset为相比原本数量的偏移量，new quantity = old quantity + offset
    //如果传进来的item的quantity已经是新的了，那就不需要offset，直接把第一个参数设为item.getQuantity就行了
    public void addItemQuantity(Item item, int offset) {
        try {
            String sql = "update item_quantity set quantity = quantity + ? where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, offset);
            stmt.setLong(2, item.getId());
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 减少物资数量
     */
    //和addItemQuantity的情况一样
    //addItemQuantity和decreaseItemQuantity可以合为一种方法
    public void decreaseItemQuantity(Item item, int offset) {
        try {
            String sql = "update item_quantity set quantity = quantity - ? where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, offset);
            stmt.setLong(2, item.getId());
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 默认搜索物资
     */
    public static Item[] queryI() {
        try {
            String sql = "select * from item;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            Item[] items = new Item[10];
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong(1));
                item.setName(rs.getString(2));
                item.setType(rs.getString(3));
                item.setReturnable(rs.getBoolean(4));
                items[index] = item;
                index++;
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /**
         * 默认搜索物资
         */

    }


}