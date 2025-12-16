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
import java.sql.Date;
import java.util.List;
import quanlydatban.Model.Booking;
import java.sql.Timestamp;

import java.sql.Timestamp;
/**
 *
 * @author Admin
 */
public class BookingDao {
    
    
    public boolean addBooking(Timestamp timeStart,Timestamp timeEnd , int numGuest , String note ,int idEmp ,int idCus ){
        
        try {
            String sql ="Insert into Booking (TimeStarted,TimeEnd , guestCount ,Note , IDemploy , IDcus )"
                    + "Values(? , ? ,? , ? , ? ,? ) ";
            
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ptm = conn.prepareStatement(sql);
           
            ptm.setTimestamp(1, new java.sql.Timestamp(timeStart.getTime()));
            ptm.setTimestamp(2, new java.sql.Timestamp(timeEnd.getTime()));
            
            ptm.setInt(3, numGuest);
            ptm.setString(4, note);
            ptm.setInt(5, idEmp);
            ptm.setInt(6, idCus);

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

    public List<Booking> getAllBooking() {
        List<Booking> list = new ArrayList<>();

        // SQL THẦN THÁNH: Kết hợp bảng Booking và bảng List
        // GROUP_CONCAT: Gom các IDtable lại (ví dụ: "1, 3, 5")
        String sql = "SELECT b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, b.IDemploy, b.IDcus, "
                + "GROUP_CONCAT(l.IDtable SEPARATOR ', ') AS TableList "
                + "FROM Booking b "
                + "LEFT JOIN `List` l ON b.IDbooking = l.IDbooking " // Nhớ dấu ` ` ở tên bảng List
                + "GROUP BY b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, b.IDemploy, b.IDcus "
                + "ORDER BY b.IDbooking DESC"; // Sắp xếp đơn mới lên đầu

        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idBooking = rs.getInt("IDbooking");
                Timestamp TimeStart = rs.getTimestamp("TimeStarted");
                Timestamp TimeEnd = rs.getTimestamp("TimeEnd");
                int guestCount = rs.getInt("guestCount");
                String Note = rs.getString("Note");
                int IdEmploy = rs.getInt("IDemploy");
                int IDcus = rs.getInt("IDcus");

                // Lấy chuỗi danh sách bàn từ SQL
                String tableListStr = rs.getString("TableList");
                if (tableListStr == null) {
                    tableListStr = "Chưa xếp"; // Xử lý nếu null
                }
                // Tạo đối tượng Booking
                Booking b = new Booking(idBooking,TimeStart, TimeEnd, guestCount, Note, IdEmploy, IDcus);

                // Set danh sách bàn vào đối tượng
                b.setListTables(tableListStr);

                list.add(b);
            }
            conn.close();
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public void addList_Booking(int idBooking ,  List<Integer> idTable ){
        try {
            String sql ="Insert into List(IDbooking , IDtable ) "
                    + "Values(? , ?)";
            
            Connection conn = ConnectionDatabase.getConnection();
            
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1 ,idBooking);
            for(Integer x: idTable){
                pstm.setInt(2 , x);
                pstm.executeUpdate();
            }
            
            
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    public int getIDBooking(int idcus) {
        int idBooking = 0;
        try {
            // SỬA: Lấy đơn mới nhất (ID lớn nhất) của khách hàng này
            // Cách này an toàn tuyệt đối, không sợ lệch giờ/phút/giây
            String sql = "Select IDbooking From Booking Where IDcus = ? Order By IDbooking DESC LIMIT 1";

            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idcus);
            // Không cần set TimeStarted nữa

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                idBooking = rs.getInt("IDbooking");
            }
            conn.close();
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idBooking; // Đảm bảo trả về ID > 0
    }
    
    public int getCountBookingComplete(){
        int count = 0 ;
        try {
            String sql = "Select Count(IDbooking) as SoDonHoanThanh"
           + " From Booking "
           + "Where isComplete = 'Hoàn Thành' ";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs =pstm.executeQuery();
            if(rs.next()){
                count= rs.getInt("SoDonHoanThanh");
                return count;
            }
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
        
    }
    public int getCountBooking(){
        int count = 0 ;
        try {
            
            String sql ="Select Count(*) As SoDonBooking "
                    + "From Booking;";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs= pstm.executeQuery();
            if(rs.next()){
                count = rs.getInt("SoDonBooking");
            }
            return count;
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
        
    }
    
    public boolean updateIsComplete(int idBooking){
        try {
            String sql="Update Booking "
                    + "Set isComplete ='Hoàn Thành' "
                    + "Where IDbooking = ?";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1,idBooking);
            pstm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
}
