/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlydatban.Model.Booking;
/**
 *
 * @author Admin
 */
public class BookingDao {
    
    public boolean addBooking(String timeStart,String timeEnd , int numGuest , String note ,int idEmp ,int idCus  , int idTable){
        try {
            String sql ="Insert into Booking (TimeStarted,TimeEnd , guestCount ,Note , IDemploy , IDcus , IDtable )"
                    + "Values(?,?, ? ,? , ? , ? ,? ) ";
            
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ptm = conn.prepareStatement(sql);
            
            ptm.setString(1, timeStart);
            ptm.setString(2, timeEnd);
            ptm.setInt(3, numGuest);
            ptm.setString(4, note);
            ptm.setInt(5, idEmp);
            ptm.setInt(6, idCus);
            ptm.setInt(7 , idTable);
            
            ptm.executeUpdate();
                    
            
            conn.close();
            ptm.close();
            
            return true;
            
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    public void updateStatusTable(String status , int id){
        String sql ="Update DiningTable "
                + "Set statusTable = ? "
                + "Where IDtable = ? ";
        
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm =conn.prepareStatement(sql);
            pstm.setString(1, status);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    public List<Booking> getAllBooking(){
        List<Booking> list = new ArrayList<Booking>();
        String sql ="Select IDbooking , TimeStarted , guestCount , Note , IDtable , IDemploy , IDcus "
                + "From Booking";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                int idBooking = rs.getInt("IDbooking");
                String TimeStart = rs.getString("TimeStarted");
                int guestCount = rs.getInt("guestCount");
                String Note = rs.getString("Note");
                int IdEmploy = rs.getInt("IDemploy");
                int IDcus = rs.getInt("IDcus");
                int IDtable = rs.getInt("IDtable");
                
                list.add(new Booking(idBooking,TimeStart,guestCount,Note, IdEmploy, IDcus ,  IDtable));
                
            }
            conn.close();
            pstm.close();
            
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return list;
    }
    public void DeleteBooking(int idBooking){
        String sql="Delete From Booking "
                + "Where IDbooking = ? ";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            
            pstm.setInt(1,idBooking);
            pstm.executeUpdate();
            
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
