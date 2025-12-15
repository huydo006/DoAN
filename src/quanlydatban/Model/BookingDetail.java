/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

/**
 *
 * @author Admin
 */
public class BookingDetail {
    private int idBooking;
    private java.sql.Timestamp timeStart; 
private java.sql.Timestamp timeEnd;
    private int guestCount;
    private String note;
    private int idTable; 
    private String nameCus;
    private String cusPhone;

    public BookingDetail(int idBooking, java.sql.Timestamp timeStart, java.sql.Timestamp timeEnd, int guestCount, String note, int idTable, String nameCus, String cusPhone) {
        this.idBooking = idBooking;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.guestCount = guestCount;
        this.note = note;
        this.idTable = idTable;
        this.nameCus = nameCus;
        this.cusPhone = cusPhone;
    }
    
    // Getters
    public int getIdBooking() { return idBooking; }
    public java.sql.Timestamp getTimeStart() { return timeStart; }
    public java.sql.Timestamp getTimeEnd() { return timeEnd; }
    // >>> THÊM PHƯƠNG THỨC TÍNH TOÁN DURATION <<<
public String getDurationString() {
    if (timeStart == null || timeEnd == null) {
        return "N/A";
    }
    
    // Tính toán khoảng thời gian (tính bằng mili giây)
    long durationMs = timeEnd.getTime() - timeStart.getTime(); 
    
    // Xử lý trường hợp TimeEnd < TimeStart (qua nửa đêm, có thể không áp dụng
    // nếu bạn chỉ lưu TIME, nhưng tốt hơn là nên kiểm tra)
    if (durationMs < 0) {
        // Giả sử qua nửa đêm: thêm 24 giờ (86,400,000 ms)
        durationMs += 24 * 60 * 60 * 1000; 
    }
    
    long hours = durationMs / (60 * 60 * 1000);
    long minutes = (durationMs % (60 * 60 * 1000)) / (60 * 1000);
    
    return String.format("%d giờ %d phút", hours, minutes);
}
    public int getGuestCount() { return guestCount; }
    public String getNote() { return note; }
    public int getIdTable() { return idTable; }
    public String getNameCus() { return nameCus; }
    public String getCusPhone() { return cusPhone; }
}
