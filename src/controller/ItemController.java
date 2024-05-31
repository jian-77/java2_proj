
package controller;
import entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController {

    Connection conn=DatabaseConnection.getConn();

    public void addItemType(Item item){
        try{
            String sql="insert into item(name,type,returnable) values (?,?,?) returning id";
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.setString(1,item.getName());
            stmt.setString(2, item.getType());
            stmt.setBoolean(3, item.isReturnable());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                item.setId(rs.getLong("id"));}
            else{
                System.out.println("no generate id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
