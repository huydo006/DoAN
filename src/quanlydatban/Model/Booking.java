/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Booking {

    private int idBooking;
    private Timestamp TimeStart;
    private Timestamp TimeEnd;
    private int guestCount;
    private String Note;
    private int IdEmploy;
    private int IDcus;
    private String isComplete; // Trạng thái: Đã xác nhận, Hoàn thành, Đã hủy

    private String nameCus;
    private String cusPhone;
    private String listTables;

    // Constructor đầy đủ để gộp mọi thông tin (Dùng cho hiển thị)
    public Booking(int idBooking, Timestamp TimeStart, Timestamp TimeEnd, int guestCount,
            String Note, int IdEmploy, int IDcus, String isComplete,
            String nameCus, String cusPhone, String listTables) {
        this.idBooking = idBooking;
        this.TimeStart = TimeStart;
        this.TimeEnd = TimeEnd;
        this.guestCount = guestCount;
        this.Note = Note;
        this.IdEmploy = IdEmploy;
        this.IDcus = IDcus;
        this.isComplete = isComplete;
        this.nameCus = nameCus;
        this.cusPhone = cusPhone;
        this.listTables = listTables;
    }

    public Booking(int idBooking, Timestamp TimeStart, Timestamp TimeEnd, int guestCount, String Note, int IdEmploy, int IDcus) {
       this.idBooking = idBooking;
    this.TimeStart = TimeStart; 
    this.TimeEnd = TimeEnd;     
    this.guestCount = guestCount;
    this.Note = Note;
    this.IdEmploy = IdEmploy;
    this.IDcus = IDcus;
    }

    // Hàm tiện ích để hiển thị thời gian trên JTable
    public String getTimeRangeDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (TimeStart == null || TimeEnd == null) {
            return "N/A";
        }
        return sdf.format(TimeStart) + " - " + sdf.format(TimeEnd);
    }

    // --- Getters & Setters ---
    public int getIdBooking() {
        return idBooking;
    }

    public Timestamp getTimeStart() {
        return TimeStart;
    }

    public Timestamp getTimeEnd() {
        return TimeEnd;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public String getNote() {
        return Note;
    }

    public int getIDcus() {
        return IDcus;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getNameCus() {
        return nameCus;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public String getListTables() {
        return listTables;
    }

    public void setListTables(String listTables) {
        this.listTables = listTables;
    }
}
