use demo002

Select*from DiningTable
Select*from Account
Select  * from List
Select * from Customer
select *from booking

delete from account 
where username =''

SELECT
    B.IDbooking,
    L.IDtable,        -- Lấy ID bàn từ bảng trung gian List
    C.nameCus,        -- Lấy tên khách hàng
    C.cusPhone,       -- Lấy SĐT khách hàng
    B.TimeStarted,
    B.TimeEnd,
    B.guestCount,
    B.Note
FROM
    Booking AS B
INNER JOIN
    Customer AS C ON B.IDcus = C.IDcus  -- Nối Booking với Customer
INNER JOIN
    List AS L ON B.IDbooking = L.IDbooking -- Nối Booking với List (để lấy IDtable)
ORDER BY
    B.IDbooking, L.IDtable;    
    

SET FOREIGN_KEY_CHECKS = 0;   -- Tạm Tắt Khóa Ngoại
TRUNCATE TABLE Customer;		-- Xóa DỮ liệu của Bảng
TRUNCATE TABLE List;
TRUNCATE TABLE Booking
SET FOREIGN_KEY_CHECKS = 1;   
DROP table BOOKING