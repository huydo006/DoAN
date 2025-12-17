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

    
    private final String SELECT_BASE
            = "SELECT b.*, c.nameCus, c.cusPhone, GROUP_CONCAT(l.IDtable SEPARATOR ', ') AS TableList "
            + "FROM Booking b "
            + "JOIN Customer c ON b.IDcus = c.IDcus "
            + "LEFT JOIN List l ON b.IDbooking = l.IDbooking ";

    
    private Booking mapToFullBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("IDbooking"),
                rs.getTimestamp("TimeStarted"),
                rs.getTimestamp("TimeEnd"),
                rs.getInt("guestCount"),
                rs.getString("Note"),
                rs.getInt("IDemploy"),
                rs.getInt("IDcus"),
                rs.getString("isComplete"),
                rs.getString("nameCus"),
                rs.getString("cusPhone"),
                rs.getString("TableList")
        );
    }

    // --- NHÓM HÀM TRUY VẤN DỮ LIỆU ---
    
    public List<Booking> getBookingsWithFullDetails(String keyword) {
        List<Booking> list = new ArrayList<>();
        String sql = SELECT_BASE;

        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE c.nameCus LIKE ? OR c.cusPhone LIKE ? ";
        }

        sql += " GROUP BY b.IDbooking ORDER BY b.IDbooking DESC";

        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.isEmpty()) {
                pstm.setString(1, "%" + keyword + "%");
                pstm.setString(2, "%" + keyword + "%");
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToFullBooking(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    
    public boolean addBooking(Timestamp timeStart, Timestamp timeEnd, int numGuest, String note, int idEmp, int idCus) {
        String sql = "INSERT INTO Booking (TimeStarted, TimeEnd, guestCount, Note, IDemploy, IDcus) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement ptm = conn.prepareStatement(sql)) {
            ptm.setTimestamp(1, timeStart);
            ptm.setTimestamp(2, timeEnd);
            ptm.setInt(3, numGuest);
            ptm.setString(4, note);
            ptm.setInt(5, idEmp);
            ptm.setInt(6, idCus);
            return ptm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE Booking SET isComplete = ? WHERE IDbooking = ?";
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, status);
            pstm.setInt(2, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void addList_Booking(int idBooking, List<Integer> idTable) {
        String sql = "INSERT INTO List(IDbooking, IDtable) VALUES(?, ?)";
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            for (Integer x : idTable) {
                pstm.setInt(1, idBooking);
                pstm.setInt(2, x);
                pstm.executeUpdate();
                updateStatusTable("Đã Đặt", x); // Cập nhật trạng thái bàn ăn
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateStatusTable(String status, int id) {
        String sql = "UPDATE DiningTable SET statusTable = ? WHERE IDtable = ?";
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, status);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- NHÓM HÀM TIỆN ÍCH ---
    public int getIDBooking(int idcus) {
        String sql = "SELECT IDbooking FROM Booking WHERE IDcus = ? ORDER BY IDbooking DESC LIMIT 1";
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, idcus);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("IDbooking");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getCount(String status) {
        String sql = "SELECT COUNT(*) FROM Booking" + (status != null ? " WHERE isComplete = ?" : "");
        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            if (status != null) {
                pstm.setString(1, status);
            }
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Booking> getConflictingBookings(List<Integer> idTableList) {
        List<Booking> list = new ArrayList<>();
        if (idTableList == null || idTableList.isEmpty()) {
            return list;
        }

        String placeholders = String.join(",", java.util.Collections.nCopies(idTableList.size(), "?"));

        // Sử dụng UPPER để không phân biệt chữ hoa/thường trong Database
        String sql = "SELECT DISTINCT b.* FROM Booking b "
                + "JOIN List l ON b.IDbooking = l.IDbooking "
                + "WHERE l.IDtable IN (" + placeholders + ") "
                + "AND (UPPER(b.isComplete) = 'ĐÃ XÁC NHẬN' OR UPPER(b.isComplete) = 'HOÀN THÀNH')";

        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            for (int i = 0; i < idTableList.size(); i++) {
                pstm.setInt(i + 1, idTableList.get(i));
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking(
                            rs.getInt("IDbooking"),
                            rs.getTimestamp("TimeStarted"),
                            rs.getTimestamp("TimeEnd"),
                            rs.getInt("guestCount"),
                            rs.getString("Note"),
                            rs.getInt("IDemploy"),
                            rs.getInt("IDcus")
                    );

                    
                    b.setIsComplete(rs.getString("isComplete"));

                    list.add(b);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
