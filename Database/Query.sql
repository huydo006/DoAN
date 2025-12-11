use demo002

Select*from DiningTable
Select*from Account
Select * from Customer


SELECT
    B.IDbooking,
    B.IDtable,
    C.nameCus,
    C.cusPhone,
    B.TimeStarted,
    B.TimeEnd,
    B.guestCount,
    B.Note
FROM
    Booking AS B  -- Bảng bên trái (Đặt bàn)
INNER JOIN
    Customer AS C ON B.IDcus = C.IDcus  -- Nối với bảng Khách hàng qua IDcus
ORDER BY
    B.IDbooking; -- Tùy chọn: Sắp xếp theo ID Booking
    

SET FOREIGN_KEY_CHECKS = 0;   -- Tạm Tắt Khóa Ngoại
TRUNCATE TABLE Booking;		-- Xóa DỮ liệu của Bảng