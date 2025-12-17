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

import quanlydatban.Service.TableService;
import quanlydatban.Model.Table;
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

    private List<Booking> list;
    private BookingDao bookingDao = new BookingDao();

    // --- CÁC HÀM HIỂN THỊ DỮ LIỆU (SỬA THEO DAO MỚI) ---
    /**
     * Lấy toàn bộ danh sách đặt bàn cho pnScreenDanhSach
     */
    public List<Booking> getAllBooking() {
        // Sử dụng hàm gộp trong DAO với keyword = null để lấy tất cả
        list = bookingDao.getBookingsWithFullDetails(null);
        return list;
    }

    /**
     * Tìm kiếm đơn đặt bàn cho pnScreenTimKiem
     */
    public List<Booking> searchBooking(String keyword) {
        // Sử dụng chung hàm gộp trong DAO nhưng truyền từ khóa tìm kiếm
        return bookingDao.getBookingsWithFullDetails(keyword);
    }

    // --- CÁC HÀM CẬP NHẬT TRẠNG THÁI (SỬA THEO DAO MỚI) ---
    public boolean setIsComplete(int id) {
        // Sử dụng hàm updateStatus dùng chung của DAO
        return bookingDao.updateStatus(id, "Hoàn Thành");
    }

    public boolean CancelBooking(int id) {
        // Sử dụng hàm updateStatus dùng chung của DAO
        return bookingDao.updateStatus(id, "Đã Hủy");
    }

    // --- CÁC HÀM NGHIỆP VỤ (GIỮ NGUYÊN LOGIC CŨ) ---
    public boolean inserBooking(Timestamp timeStart, Timestamp timeEnd, int seat, String note, int idEmp, int idCus, List<Integer> idTable) {
        // 1. KIỂM TRA SỨC CHỨA
    TableService ts = new TableService();
    List<Table> allTables = ts.getTbList(); // Đảm bảo hàm này tồn tại trong TableService
    int totalCapacity = 0;

    if (allTables != null) {
        for (Integer selectedId : idTable) {
            for (Table t : allTables) {
                if (t.getIdTable() == selectedId) {
                    totalCapacity += t.getSeats();
                    break;
                }
            }
        }
    }

    if (seat > totalCapacity) {
        JOptionPane.showMessageDialog(null, 
            "Lỗi sức chứa: Bàn đã chọn chỉ có " + totalCapacity + " chỗ, không đủ cho " + seat + " khách!", 
            "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // 2. KIỂM TRA TRÙNG LỊCH (ĐÃ ĐẶT CHƯA)
    if (checkTimeConflict(timeStart, timeEnd, idTable)) {
        // Thông báo "Bàn đã được đặt" nằm trong hàm checkTimeConflict rồi
        return false;
    }

    // 3. LƯU VÀO DATABASE
    if (bookingDao.addBooking(timeStart, timeEnd, seat, note, idEmp, idCus)) {
        int idBooking = bookingDao.getIDBooking(idCus);
        addTable_Booking(idBooking, idTable);
        return true;
    }
    return false;
    }

    private boolean checkTimeConflict(Timestamp newTimeStart, Timestamp newTimeEnd, List<Integer> idTableList) {
       if (!newTimeEnd.after(newTimeStart)) {
        JOptionPane.showMessageDialog(null, "Giờ kết thúc phải sau giờ bắt đầu!");
        return true;
    }

    final long GAP_MS = 20 * 60 * 1000; // 20 phút nghỉ
    List<Booking> existingBookings = bookingDao.getConflictingBookings(idTableList);

    for (Booking old : existingBookings) {
        // Lấy giá trị Long để so sánh chính xác tuyệt đối
        long oldEndWithGap = old.getTimeEnd().getTime() + GAP_MS;
        long oldStart = old.getTimeStart().getTime();
        long newStart = newTimeStart.getTime();
        long newEnd = newTimeEnd.getTime();

        // Logic va chạm: Đơn mới bắt đầu TRƯỚC mốc (Kết thúc cũ + 20p) 
        // VÀ đơn mới kết thúc SAU khi đơn cũ bắt đầu
        if (newStart < oldEndWithGap && newEnd > oldStart) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String timeSuggest = sdf.format(new Date(oldEndWithGap));
            
            JOptionPane.showMessageDialog(null, 
                "Bàn này đã có đơn phục vụ đến " + sdf.format(old.getTimeEnd()) + ".\n" +
                "Theo quy định nghỉ 20p, bạn chỉ có thể đặt từ " + timeSuggest + " trở đi.", 
                "Lỗi Trùng Lịch", JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }
    return false;
    }

    public void addTable_Booking(int idBooking, List<Integer> idTable) {
        bookingDao.addList_Booking(idBooking, idTable);
        // Lưu ý: Việc updateStatusTable cho từng bàn đã được thực hiện bên trong DAO addList_Booking
    }

    public int getCountBooking() {
        return bookingDao.getCount(null);
    }

    public int getCount(String status) {
        return bookingDao.getCount(status);
    }

}
