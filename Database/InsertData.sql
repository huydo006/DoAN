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

INSERT INTO DiningTable (seats) VALUES
(4),
(4),
(6),
(6),
(8),
(8),
(10),
(10),
(12),
(12);


INSERT INTO Employee (Name, DoB, Gender, Phone, Address, Salary, role)
VALUES
-- admin
('Boss', '2000-1-1', 'Nam', '0123456789', 'Hà Nội', 10000000.00, 'Manager');

INSERT INTO Account (username, password, IDemploy)
VALUES (
    'admin',
    '123',
    (SELECT IDemploy FROM Employee WHERE Name = 'Boss' LIMIT 1)
);


