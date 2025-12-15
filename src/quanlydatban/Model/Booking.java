/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author HELLO
 */
public class Booking {
    int idBooking;
    
    Timestamp TimeStart;
    Timestamp TimeEnd;
    int guestCount;
    String Note;
    
    //PK
    int IdEmploy ;
    int IDcus;

    public Booking(int idBooking, Timestamp TimeStart, Timestamp TimeEnd, int guestCount, String Note, int IdEmploy, int IDcus) {
        this.idBooking = idBooking;
        this.TimeStart = TimeStart;
        this.TimeEnd = TimeEnd;
        this.guestCount = guestCount;
        this.Note = Note;
        this.IdEmploy = IdEmploy;
        this.IDcus = IDcus;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public Timestamp getTimeStart() {
        return TimeStart;
    }

    public void setTimeStart(Timestamp TimeStart) {
        this.TimeStart = TimeStart;
    }

    public Timestamp getTimeEnd() {
        return TimeEnd;
    }

    public void setTimeEnd(Timestamp TimeEnd) {
        this.TimeEnd = TimeEnd;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public int getIdEmploy() {
        return IdEmploy;
    }

    public void setIdEmploy(int IdEmploy) {
        this.IdEmploy = IdEmploy;
    }

    public int getIDcus() {
        return IDcus;
    }

    public void setIDcus(int IDcus) {
        this.IDcus = IDcus;
    }

    
    
}