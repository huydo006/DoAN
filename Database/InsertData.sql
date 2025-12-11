use demo002
INSERT INTO DiningTable (seats, statusTable) VALUES
(4,  'Trống'),
(2,  'Trống'),
(6,  'Trống'),
(8,  'Trống'),
(4,  'Trống'),
(10, 'Trông'),
(2,  'Trống'),
(6,  'Trống'),
(12, 'Trống'),
(4,  'Trống'),
(8,  'Trống');

Insert into Account (username , password) 
values('doquanghuy' , '11'),
('phambinhhoan' , '22'),
('trinhquangtruong' ,'33'),
('phamngoctu' , '44');

INSERT INTO Employee (Name, DoB, Gender, Phone, Address, Salary, role, username)
VALUES
-- Đỗ Quang Huy
('Đỗ Quang Huy', '2000-05-15', 'Nam', '0901234567', '123 Đường Nguyễn Trãi, Hà Nội', 10000000.00, 'Employee', 'doquanghuy'),

-- Trịnh Quang Trường
('Trịnh Quang Trường', '1999-11-20', 'Nam', '0919876543', '456 Phố Điện Biên Phủ, Đà Nẵng', 12500000.00, 'Employee', 'trinhquangtruong'),

-- Phạm Bình Hoàn
('Phạm Bình Hoàn', '2001-08-01', 'Nam', '0987654321', '789 Quận 1, TP. Hồ Chí Minh', 9800000.00, 'Employee', 'phambinhhoan'),

-- Phạm Ngọc Tú
('Phạm Ngọc Tú', '1998-03-25', 'Nam', '0971122334', '101 Đường Trần Hưng Đạo, Cần Thơ', 11200000.00, 'Employee', 'phamngoctu');

