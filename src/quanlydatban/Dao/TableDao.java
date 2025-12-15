package quanlydatban.Dao;

import java.util.List;
import quanlydatban.Model.Table;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HELLO
 */
public class TableDao {
    public TableDao(){
        
    }
    public List<Table> getAllTable (){
        List<Table> list = new ArrayList<Table>();
        String sql ="Select *from DiningTable ";
        
        try {
            Connection conn = ConnectionDatabase.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                String id = rs.getString("IDtable");
                String seat = rs.getString("seats");
                String mode =rs.getString("statusTable");
                
                list.add(new Table(id,seat,mode));
                
                
            }
            conn.close();
            st.close();
            
        } catch (SQLException ex) {
            System.getLogger(TableDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return list;
    }
    
    public void updateStatus(String id , String newStatus){
        String sql ="Update DiningTable "
                + "Set statusTable = ?"
                + "Where idTable = ?";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            
            pstm.setString(1,newStatus);
            pstm.setString(2,id);
            
            pstm.executeUpdate();
            pstm.close();
            conn.close();
            
        } catch (SQLException ex) {
            System.getLogger(TableDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
