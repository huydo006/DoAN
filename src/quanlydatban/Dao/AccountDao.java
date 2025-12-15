/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Dao;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import quanlydatban.Model.Account;

/**
 *
 * @author HELLO
 */
public class AccountDao {
    public List<Account> getAllAcount(){
        List<Account> listAcc = new ArrayList<Account>();
        
        //B1 viết lệnh SQL
        String sql = "Select * from account";
        
        try {
            //B2 tạo kết nối
            Connection conn = ConnectionDatabase.getConnection();
            
            //b3 tạo stm
            Statement stm = conn.createStatement();
            
            //b4 tạo Rs lấy data
            ResultSet rs= stm.executeQuery(sql);
            
            while(rs.next()){
                String user= rs.getString("username");
                String pass = rs.getString("password");
                Boolean isActive = rs.getBoolean("isActive");
                
                listAcc.add(new Account(isActive, user , pass ));
            }
            rs.close();
            stm.close();
            
            
        } catch (SQLException ex) {
            System.getLogger(AccountDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return listAcc;
    }
    public Account getAccountByUser(String user){
        Account acc = null;
        String sql = "Select * from Account "
                + "Where username = ?";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement psm = conn.prepareStatement(sql);
            
            psm.setString(1, user);
            ResultSet rs= psm.executeQuery();
            if(rs.next()){
                String users = rs.getString("username");
                String pass = rs.getString("password");
                
                acc = new Account(users, pass);
            }
            
            
        } catch (SQLException ex) {
            System.getLogger(AccountDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return acc;
        
    }
    public void updateActive(boolean isActive,String username){
        String sql = "UPDATE `Account` " 
               + "SET `isActive` = ? " // Đảm bảo có khoảng trắng sau dấu ? và trước WHERE
               + "WHERE `username` = ?";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement psm= conn.prepareStatement(sql);
            
            psm.setBoolean(1, isActive);
            psm.setString(2, username);
            psm.executeUpdate();
            
            psm.close();
            conn.close();
            
            
            
        } catch (SQLException ex) {
            System.getLogger(AccountDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
