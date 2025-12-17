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

    private int idTable; 
    private int seats;   
    private String statusTable;

    public Table(int idTable, int seats, String statusTable) {
        this.idTable = idTable;
        this.seats = seats;
        this.statusTable = statusTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

    public int getIdTable() {
        return idTable;
    }

    public int getSeats() {
        return seats;
    }

    public String getStatusTable() {
        return statusTable;
    }

}
