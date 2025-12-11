create database demo002
use demo002

drop database demo002
select * from Booking
-- BẢNG ACCOUNT
CREATE TABLE `Account` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `isActive` Bool ,
  PRIMARY KEY (`username`)
) ;


-- BẢNG EMPLOYEE (role là ENUM)
CREATE TABLE `Employee` (
  `IDemploy` INT NOT NULL AUTO_INCREMENT,
  `Name` NVARCHAR(100) NOT NULL,
  `DoB` DATE,
  `Gender` VARCHAR(10),
  `Phone` VARCHAR(20),
  `Address` NVARCHAR(200),
  `Salary` DECIMAL(12,2),
  `role` ENUM('Employee','Manager') NOT NULL,
  `username` VARCHAR(50) UNIQUE,              -- 1-1 với Account
  PRIMARY KEY (`IDemploy`),
  CONSTRAINT `fk_employee_account` FOREIGN KEY (`username`)
    REFERENCES `Account` (`username`)
    
) ;

-- BẢNG CUSTOMER
CREATE TABLE `Customer` (
  `IDcus` INT NOT NULL AUTO_INCREMENT,
  `nameCus` NVARCHAR(100) NOT NULL,
  `cusPhone` VARCHAR(20),
  PRIMARY KEY (`IDcus`)
);

-- BẢNG DiningTable (statusTable là ENUM)
CREATE TABLE `DiningTable` (
  `IDtable` INT NOT NULL AUTO_INCREMENT,
  `seats` int NOT NULL DEFAULT 1,
  `statusTable` ENUM('Trống','Đã Đặt','Đang Dùng') NOT NULL DEFAULT 'Trống',
  PRIMARY KEY (`IDtable`)
) ;


-- BẢNG BOOKING
CREATE TABLE `Booking` (
  `IDbooking` INT NOT NULL AUTO_INCREMENT,
  `DateTimeCreateBooking` timestamp default current_timestamp,
  `TimeStarted` TIME,
  `TimeEnd` Time,
  `guestCount` INT DEFAULT 1,
  `Note` TEXT,
  `IDtable`  INT NOT NULL ,
  `IDemploy` INT NOT NULL,   -- nhân viên tạo booking (1..n)
  `IDcus` INT NOT NULL,      -- khách hàng (1..n)
  PRIMARY KEY (`IDbooking`),
  CONSTRAINT `fk_booking_employee` FOREIGN KEY (`IDemploy`)
    REFERENCES `Employee` (`IDemploy`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_customer` FOREIGN KEY (`IDcus`)
    REFERENCES `Customer` (`IDcus`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





