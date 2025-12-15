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
        int idBooking;
        if(bookingDao.addBooking(timeStart , timeEnd ,seat , note , idEmp , idCus)){
            idBooking =  bookingDao.getIDBooking(idCus);
            addTable_Booking(idBooking ,idTable );
            return true;
        } 
        return false;
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
    public void ResetStatus(int id){
        bookingDao.updateStatusTable("Trống", id);
    }
    public void setCompleteBooking(){
        
    }
    public void DeteteBooking(int id){
        bookingDao.DeleteBooking(id);
    }
//    public boolean checkTimeValid(String newStartStr, String newEndStr, int idTable) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            // 1. CHUYỂN ĐỔI STRING SANG DATE
//            Date newStart = sdf.parse(newStartStr);
//            Date newEnd = sdf.parse(newEndStr);
//
//            // =================================================================
//            // ĐIỀU KIỆN 1: KIỂM TRA LOGIC CỦA CHÍNH ĐƠN MỚI
//            // =================================================================
//            // Check A: Giờ kết thúc phải sau giờ bắt đầu
//            if (!newEnd.after(newStart)) {
//                JOptionPane.showMessageDialog(null, "Giờ kết thúc phải sau giờ bắt đầu!");
//                return false;
//            }
//
//            // Check B: Thời gian ăn tối thiểu phải là 30 phút (Ví dụ)
//            long durationMs = newEnd.getTime() - newStart.getTime(); // Ra mili giây
//            long durationMinutes = durationMs / (60 * 1000); // Đổi ra phút
//
//            if (durationMinutes < 30) {
//                JOptionPane.showMessageDialog(null, "Thời gian đặt bàn quá ngắn (Tối thiểu 30 phút)!");
//                return false;
//            }
//
//            // =================================================================
//            // ĐIỀU KIỆN 2: KIỂM TRA TRÙNG VÀ KHOẢNG CÁCH 20 PHÚT VỚI ĐƠN CŨ
//            // =================================================================
//            // Bước 1: Lấy danh sách tất cả các đơn ĐANG HOẠT ĐỘNG của bàn này từ DB
//            // (Giả sử bạn đã có hàm getBookingsByTable trong DAO)
//            BookingDao dao = new BookingDao();
//            List<Booking> listOld = dao.getBookingsByTable(idTable);
//
//            // Khoảng cách an toàn cần thiết (20 phút đổi ra mili giây)
//            long gapSafe = 20 * 60 * 1000;
//
//            // Bước 2: Duyệt qua từng đơn cũ để so sánh
//            for (Booking old : listOld) {
//                // Chỉ so sánh các đơn chưa hủy
//                if (old.getStatus().equals("Cancelled")) {
//                    continue;
//                }
//
//                Date oldStart = sdf.parse(old.getTimeStart());
//                Date oldEnd = sdf.parse(old.getTimeEnd());
//
//                // --- CÔNG THỨC KIỂM TRA VA CHẠM (QUAN TRỌNG) ---
//                // Một đơn mới bị coi là PHẠM QUY nếu nó vi phạm khoảng đệm 20p của đơn cũ.
//                // Tức là: Nó bắt đầu TRƯỚC khi đơn cũ dọn xong 20p 
//                // VÀ kết thúc SAU khi đơn cũ bắt đầu (trừ đi 20p chuẩn bị)
//                Date oldEndPlus20 = new Date(oldEnd.getTime() + gapSafe);   // Giờ cũ xong + 20p dọn
//                Date oldStartMinus20 = new Date(oldStart.getTime() - gapSafe); // Giờ cũ đến - 20p chuẩn bị
//
//                // Nếu (Mới_Bắt_Đầu < Cũ_Xong_Xuôi) VÀ (Mới_Kết_Thúc > Cũ_Mới_Đến) => ĐỤNG ĐỘ
//                if (newStart.before(oldEndPlus20) && newEnd.after(oldStartMinus20)) {
//
//                    JOptionPane.showMessageDialog(null,
//                            "Xung đột thời gian với đơn cũ!\n"
//                            + "Đơn cũ: " + old.getTimeStart() + " - " + old.getTimeEnd() + "\n"
//                            + "Cần cách nhau ít nhất 20 phút.");
//                    return false; // Có lỗi, dừng ngay
//                }
//            }
//
//            return true; // Tất cả ok
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    
//    private boolean isValidTime(String time) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}