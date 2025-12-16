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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */

// Trong BookingService.java (Nơi xử lý logic nghiệp vụ)

public class BookingService {
    List<Booking> list;
    private BookingDao bookingDao = new BookingDao(); 
    // Khởi tạo đối tượng DAO để giao tiếp với DB

 
    public boolean inserBooking(Timestamp timeStart,Timestamp timeEnd, int seat, String note, int idEmp,int idCus ,List<Integer> idTable){
        if (checkTimeConflict(timeStart, timeEnd, idTable)) {
            return false; // Dừng lại nếu có xung đột
        }
        int idBooking;
        if(bookingDao.addBooking(timeStart , timeEnd ,seat , note , idEmp , idCus)){
            idBooking =  bookingDao.getIDBooking(idCus);
            addTable_Booking(idBooking ,idTable );
            return true;
        } 
        return false;
    }
    private boolean checkTimeConflict(Timestamp newTimeStart, Timestamp newTimeEnd, List<Integer> idTableList) {
        
        // 30 phút = 30 * 60 * 1000 mili giây
        final long SAFE_GAP_MS = 30 * 60 * 1000; 

        // 1. Lấy tất cả các đơn đặt bàn có liên quan đến các bàn này
        List<Booking> oldBookings = bookingDao.getConflictingBookings(idTableList, newTimeStart, newTimeEnd);

        // 2. Duyệt qua từng đơn cũ để kiểm tra va chạm
        for (Booking oldBooking : oldBookings) {
            Timestamp oldTimeStart = oldBooking.getTimeStart();
            Timestamp oldTimeEnd = oldBooking.getTimeEnd();

            // Tính toán thời gian kết thúc của đơn cũ CỘNG THÊM 30 phút
            long oldEndPlusGapMs = oldTimeEnd.getTime() + SAFE_GAP_MS;
            Timestamp oldEndPlusGap = new Timestamp(oldEndPlusGapMs);
            
            // Tính toán thời gian bắt đầu của đơn cũ TRỪ ĐI 30 phút
            long oldStartMinusGapMs = oldTimeStart.getTime() - SAFE_GAP_MS;
            Timestamp oldStartMinusGap = new Timestamp(oldStartMinusGapMs);

            // CÔNG THỨC VA CHẠM CÓ KHOẢNG ĐỆM 30P
            // Va chạm nếu: 
            // - Giờ BẮT ĐẦU MỚI (newTimeStart) nằm trước GIỜ KẾT THÚC CŨ CÓ ĐỆM (oldEndPlusGap) 
            // - VÀ Giờ KẾT THÚC MỚI (newTimeEnd) nằm sau GIỜ BẮT ĐẦU CŨ CÓ ĐỆM (oldStartMinusGap)
            
            // Nếu newStart < oldEnd + 30p VÀ newEnd > oldStart - 30p => XUNG ĐỘT
            if (newTimeStart.before(oldEndPlusGap) && newTimeEnd.after(oldStartMinusGap)) {
                // Xung đột xảy ra.
                JOptionPane.showMessageDialog(null, 
                    "Xung đột lịch đặt bàn!\n" +
                    "Bàn đã chọn bị trùng với đơn đặt: " + oldBooking.getIdBooking() + "\n" +
                    "Thời gian đặt bị trùng: " + oldTimeStart.toString() + " - " + oldTimeEnd.toString() + "\n" +
                    "Yêu cầu khoảng cách tối thiểu 30 phút giữa các đơn.", 
                    "Lỗi Xung Đột Thời Gian", JOptionPane.ERROR_MESSAGE);
                return true; // Bị trùng, trả về true
            }
        }

        // 3. Kiểm tra logic đơn hàng: Giờ kết thúc phải sau giờ bắt đầu
        if (!newTimeEnd.after(newTimeStart)) {
            JOptionPane.showMessageDialog(null, "Giờ kết thúc phải sau giờ bắt đầu!", "Lỗi Thời Gian", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        return false; // Không bị trùng, trả về false
    }
    public void addTable_Booking(int idBooking ,List<Integer> idTable){
        bookingDao.addList_Booking(idBooking, idTable);
        for(Integer x: idTable){
            bookingDao.updateStatusTable("Đã Đặt", x);
        }   
    }
    public List<Booking> getAllBooking() {
        list= bookingDao.getAllBooking();
        return list;
    }
    
    public int getCountBooking(){
        return bookingDao.getCountBooking();
    }
    public int getCount(String status){
        return bookingDao.getCount(status);
    }
    public boolean setIsComplete(int id){
        return bookingDao.updateIsComplete(id);
    }
    public boolean CancelBooking(int id){
        return bookingDao.CancelBooking(id);
    }
    
    
}