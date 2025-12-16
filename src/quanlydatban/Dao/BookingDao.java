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
import quanlydatban.Model.BookingDetail;

import java.sql.Timestamp;
/**
 *
 * @author Admin
 */
public class BookingDao {

    public boolean addBooking(Timestamp timeStart, Timestamp timeEnd, int numGuest, String note, int idEmp, int idCus) {

        try {
            String sql = "Insert into Booking (TimeStarted,TimeEnd , guestCount ,Note , IDemploy , IDcus )"
                    + "Values(? , ? ,? , ? , ? ,? ) ";

            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ptm = conn.prepareStatement(sql);

            ptm.setTimestamp(1, new java.sql.Timestamp(timeStart.getTime()));
            ptm.setTimestamp(2, new java.sql.Timestamp(timeEnd.getTime()));

            ptm.setInt(3, numGuest);
            ptm.setString(4, note);
            ptm.setInt(5, idEmp);
            ptm.setInt(6, idCus);

            ptm.executeUpdate();

            conn.close();
            ptm.close();

            return true;

        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }

//        String sql ="Select IDbooking "
//                + "from Booking "
//                + "Where `TimeStarted` = ? AND `IDcus` =? ";
    }

    public void updateStatusTable(String status, int id) {
        String sql = "Update DiningTable "
                + "Set statusTable = ? "
                + "Where IDtable = ? ";

        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, status);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public List<Booking> getAllBooking() {
        List<Booking> list = new ArrayList<>();

        // Kết hợp bảng Booking và bảng List
        String sql = "SELECT b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, b.IDemploy, b.IDcus, "
                + "GROUP_CONCAT(l.IDtable SEPARATOR ', ') AS TableList "
                + "FROM Booking b "
                + "LEFT JOIN `List` l ON b.IDbooking = l.IDbooking "
                + "Where b.isComplete ='Đã Xác Nhận' "
                + "GROUP BY b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, b.IDemploy, b.IDcus "
                + "ORDER BY b.IDbooking DESC ";

        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idBooking = rs.getInt("IDbooking");
                Timestamp TimeStart = rs.getTimestamp("TimeStarted");
                Timestamp TimeEnd = rs.getTimestamp("TimeEnd");
                int guestCount = rs.getInt("guestCount");
                String Note = rs.getString("Note");
                int IdEmploy = rs.getInt("IDemploy");
                int IDcus = rs.getInt("IDcus");

                // Lấy chuỗi danh sách bàn từ SQL
                String tableListStr = rs.getString("TableList");

                Booking b = new Booking(idBooking, TimeStart, TimeEnd, guestCount, Note, IdEmploy, IDcus);

                // Set danh sách bàn vào đối tượng
                b.setListTables(tableListStr);

                list.add(b);
            }
            conn.close();
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public boolean CancelBooking(int idBooking) {
        String sql = "UPDATE Booking "
                + "SET isComplete = 'ĐÃ HỦY' "
                + "WHERE IDbooking = ?;";
        try {
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setInt(1, idBooking);
            pstm.executeUpdate();
            pstm.close();
            conn.close();

            return true;
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;

    }

    public void addList_Booking(int idBooking, List<Integer> idTable) {
        try {
            String sql = "Insert into List(IDbooking , IDtable ) "
                    + "Values(? , ?)";

            Connection conn = ConnectionDatabase.getConnection();

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idBooking);
            for (Integer x : idTable) {
                pstm.setInt(2, x);
                pstm.executeUpdate();
            }

            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public int getIDBooking(int idcus) {
        int idBooking = 0;
        try {
            // SỬA: Lấy đơn mới nhất (ID lớn nhất) của khách hàng này
            // Cách này an toàn tuyệt đối, không sợ lệch giờ/phút/giây
            String sql = "Select IDbooking From Booking Where IDcus = ? Order By IDbooking DESC LIMIT 1";

            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idcus);
            // Không cần set TimeStarted nữa

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                idBooking = rs.getInt("IDbooking");
            }
            conn.close();
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idBooking; // Đảm bảo trả về ID > 0
    }

    public List<Booking> getConflictingBookings(List<Integer> idTableList, Timestamp newTimeStart, Timestamp newTimeEnd) {
        List<Booking> list = new ArrayList<>();
        if (idTableList == null || idTableList.isEmpty()) {
            return list;
        }

        // Tạo chuỗi placeholders (?, ?, ...) cho IN clause
        String placeholders = String.join(",", java.util.Collections.nCopies(idTableList.size(), "?"));

        try {
            // Lấy tất cả các Booking liên quan đến các bàn này.
            // Sau đó sẽ lọc kỹ hơn (có tính đến 30p) ở tầng Service.
            // TRUY VẤN: Lấy các đơn cũ (b) đang sử dụng một trong các bàn được chọn (dt)
            // và khoảng thời gian của đơn cũ đó CÓ THỂ va chạm với khoảng thời gian mới (newTimeStart, newTimeEnd).
            // Công thức kiểm tra va chạm cơ bản: (Start1 < End2) AND (End1 > Start2)
            // Cần phải mở rộng công thức này ở tầng Service để thêm khoảng đệm 30 phút.
            // Ở đây, ta chỉ cần lấy tất cả các đơn Booking liên quan:
            String sql = "SELECT DISTINCT b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, b.IDemploy, b.IDcus "
                    + "FROM Booking b "
                    + "JOIN List l ON b.IDbooking = l.IDbooking "
                    + "WHERE l.IDtable IN (" + placeholders + ") ";

            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);

            // Set các IDtable vào placeholders
            for (int i = 0; i < idTableList.size(); i++) {
                pstm.setInt(i + 1, idTableList.get(i));
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idBooking = rs.getInt("IDbooking");
                Timestamp TimeStart = rs.getTimestamp("TimeStarted");
                Timestamp TimeEnd = rs.getTimestamp("TimeEnd");
                int guestCount = rs.getInt("guestCount");
                String Note = rs.getString("Note");
                int IdEmploy = rs.getInt("IDemploy");
                int IDcus = rs.getInt("IDcus");

                list.add(new Booking(idBooking, TimeStart, TimeEnd, guestCount, Note, IdEmploy, IDcus));
            }

            conn.close();
            pstm.close();
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return list;
    }

    private BookingDetail mapResultSetToBookingDetail(ResultSet rs) throws SQLException {
        // Lưu ý: rs.getString("TimeStarted") sẽ lấy dữ liệu Time/Timestamp từ DB
        return new BookingDetail(
                rs.getInt("IDbooking"),
                rs.getTimestamp("TimeStarted"), // <<< LẤY TIMESTAMP
                rs.getTimestamp("TimeEnd"), // <<< LẤY TIMESTAMP 
                rs.getInt("guestCount"),
                rs.getString("Note"),
                rs.getInt("IDtable"), // Lấy IDtable từ bảng List
                rs.getString("nameCus"),
                rs.getString("cusPhone")
        );
    }

    // --- PHƯƠNG THỨC 1: Tải tất cả chi tiết đặt bàn (JOIN 3 bảng) ---
    public List<BookingDetail> getAllBookingDetail() {
        List<BookingDetail> list = new ArrayList<>();

        String sql = "SELECT b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, "
                + "       c.nameCus, c.cusPhone, l.IDtable "
                + "FROM Booking b "
                + "JOIN Customer c ON b.IDcus = c.IDcus "
                + "JOIN List l ON b.IDbooking = l.IDbooking "
                + "ORDER BY b.IDbooking DESC";

        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToBookingDetail(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi tải toàn bộ Booking Detail: " + ex.getMessage());
        }
        return list;
    }

    // --- PHƯƠNG THỨC 2: Tìm kiếm Đặt bàn CHỈ THEO TÊN HOẶC SĐT KHÁCH HÀNG ---
    public List<BookingDetail> searchBookingDetail(String keyword) {
        List<BookingDetail> list = new ArrayList<>();
        String searchPattern = "%" + keyword + "%";

        // Sửa đổi SQL: CHỈ BAO GỒM NAME CUS VÀ PHONE
        String sql = "SELECT b.IDbooking, b.TimeStarted, b.TimeEnd, b.guestCount, b.Note, "
                + "       c.nameCus, c.cusPhone, l.IDtable "
                + "FROM Booking b "
                + "JOIN Customer c ON b.IDcus = c.IDcus "
                + "JOIN List l ON b.IDbooking = l.IDbooking "
                + "WHERE c.nameCus LIKE ? OR c.cusPhone LIKE ? "
                + "ORDER BY b.IDbooking DESC";

        try (Connection conn = ConnectionDatabase.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            // Chỉ đặt 2 tham số: Tên và SĐT
            pstm.setString(1, searchPattern);
            pstm.setString(2, searchPattern);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBookingDetail(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi tìm kiếm Booking Detail (Tên/SĐT): " + ex.getMessage());
        }
        return list;
    }

    public int getCountBooking() {
        int count = 0;
        try {

            String sql = "Select Count(*) As SoDonBooking "
                    + "From Booking;";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("SoDonBooking");
            }
            return count;
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;

    }

    public int getCount(String status) {
        int count = 0;
        try {
            String sql = "Select Count(*) as SoLuong "
                    + "From Booking "
                    + "Where isComplete = ? ";

            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, status);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("SoLuong");
                return count;
            }

        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return count;

    }

    public boolean updateIsComplete(int idBooking) {
        try {
            String sql = "Update Booking "
                    + "Set isComplete ='Hoàn Thành' "
                    + "Where IDbooking = ?";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idBooking);
            pstm.executeUpdate();
            pstm.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.getLogger(BookingDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
}
