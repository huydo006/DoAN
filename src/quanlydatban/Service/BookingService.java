/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import quanlydatban.Dao.BookingDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlydatban.Model.Booking;

/**
 *
 * @author Admin
 */

// Trong BookingService.java (Nơi xử lý logic nghiệp vụ)

public class BookingService {
    List<Booking> list;
    private BookingDao bookingDao = new BookingDao(); 
    // Khởi tạo đối tượng DAO để giao tiếp với DB

    public boolean insertBooking(String timeStart,String timeEnd, int seat, String note, int idEmp, int IDcus , int idTable)throws SQLException {
        
            bookingDao.updateStatusTable("Đã Đặt", idTable);
            return bookingDao.addBooking(timeStart,timeEnd, seat, note, idEmp, IDcus , idTable);
            
    }
    public List<Booking> getAllBooking() {
        list= bookingDao.getAllBooking();
        return list;
    }
    public void ResetStatus(int id){
        bookingDao.updateStatusTable("Trống", id);
    }
    public void setCompleteBooking(){
        
    }
    public void DeteteBooking(int id){
        bookingDao.DeleteBooking(id);
    }
//    private boolean isValidTime(String time) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}