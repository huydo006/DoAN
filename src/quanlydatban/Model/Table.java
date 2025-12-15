/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

/**
 *
 * @author HELLO
 */
public class Table {
    String idTable;
    String seats;
    String statusTable;

    public Table(String idTable, String seats, String statusTable) {
        this.idTable = idTable;
        this.seats = seats;
        this.statusTable = statusTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

    public String getIdTable() {
        return idTable;
    }

    public String getSeats() {
        return seats;
    }

    public String getStatusTable() {
        return statusTable;
    }
    
}
