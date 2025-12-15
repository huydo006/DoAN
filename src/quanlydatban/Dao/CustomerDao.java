/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import quanlydatban.Model.Customer;
/**
 *
 * @author Admin
 */
public class CustomerDao {
    public boolean addCustomer(String name , String numberPhone){
        
        String sql = "Insert into customer(nameCus , cusPhone) "
                + " Values(? , ?)";
        
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, name);
            ptm.setString(2, numberPhone);
            
            ptm.executeUpdate();
            conn.close();
            ptm.close();
            
            return true;
        } catch (SQLException ex) {
            System.getLogger(CustomerDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public int getIdCus(String numberPhone){
        int id = 0 ;
        String sql = "Select IDcus from Customer "
                + " Where cusPhone = ? ";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ptm = conn.prepareStatement(sql);
            
            ptm.setString(1, numberPhone);
            
            ResultSet rs= ptm.executeQuery();
            if(rs.next()){
                id = rs.getInt("IDcus");
            }
            conn.close();
            ptm.close();
            
            
        } catch (SQLException ex) {
            System.getLogger(CustomerDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    
        return id;
    }
    public Customer getCusById(int id){
        Customer temp = null;
        String sql ="Select nameCus , cusPhone "
                + "From Customer "
                + "Where IDcus = ?";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1,id);
            ResultSet rs =pstm.executeQuery();
            
            if(rs.next()){
                String name = rs.getString("nameCus");
                String phone = rs.getString("cusPhone");
                temp = new Customer(name , phone);
            }
        } catch (SQLException ex) {
            System.getLogger(CustomerDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return temp;
    }
    public void DeleteCus(int idCus){
        String sql= "Delete from Customer "
                + "Where IDcus = ?";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idCus);
            pstm.executeUpdate();
            
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.getLogger(CustomerDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
