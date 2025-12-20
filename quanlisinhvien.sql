-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2025 at 04:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlisinhvien`
--

-- --------------------------------------------------------

--
-- Table structure for table `sinhvien`
--

CREATE TABLE `sinhvien` (
  `maSV` varchar(20) NOT NULL,
  `tenSV` varchar(100) NOT NULL,
  `tuoi` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `diemJava` float DEFAULT 0,
  `diemHTML` float DEFAULT 0,
  `diemCSS` float DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sinhvien`
--

INSERT INTO `sinhvien` (`maSV`, `tenSV`, `tuoi`, `email`, `diemJava`, `diemHTML`, `diemCSS`) VALUES
('31020223021', 'Trương Công Quảng Đại', 20, 'Dai88133@gmail.com', 8.5, 7.9, 8.8),
('sdsd', 'dsdsd', 23, '5555@gmail.com', 10, 10, 10),
('SV01', 'Test Ket Noi', 20, 'test@gmail.com', 0, 0, 0),
('SV02', 'Tran Thi Bich', 21, 'sv02@gmail.com', 7, 6.5, 7.5),
('SV03', 'Le Van Cuong', 22, 'sv03@gmail.com', 9, 8.5, 9.5),
('SV04', 'Pham Thi Dung', 19, 'sv04@gmail.com', 5, 6, 5.5),
('SV05', 'Hoang Van Em', 20, 'sv05@gmail.com', 6.5, 7, 6),
('SV06', 'Dang Thi Phuong', 21, 'sv06@gmail.com', 10, 9.5, 10),
('SV07', 'Bui Van Giang', 23, 'sv07@gmail.com', 4, 5, 4.5),
('SV08', 'Do Thi Hoa', 20, 'sv08@gmail.com', 8, 8, 8),
('SV09', 'Ngo Van Hung', 22, 'sv09@gmail.com', 7.5, 7, 7.5),
('SV10', 'Vu Thi Khanh', 19, 'sv10@gmail.com', 9.5, 9, 9.5),
('SV11', 'Ly Van Long', 21, 'sv11@gmail.com', 6, 6.5, 6),
('SV12', 'Truong Thi Mai', 20, 'sv12@gmail.com', 8.5, 8, 9),
('SV13', 'Dinh Van Nam', 22, 'sv13@gmail.com', 5.5, 5, 6),
('SV14', 'Phan Thi Oanh', 19, 'sv14@gmail.com', 7, 7.5, 7),
('SV15', 'Trinh Van Phuc', 21, 'sv15@gmail.com', 10, 10, 9.5),
('SV16', 'Doan Thi Quyen', 20, 'sv16@gmail.com', 9, 9.5, 9),
('SV17', 'Lam Van Rong', 23, 'sv17@gmail.com', 4.5, 5, 4),
('SV18', 'Mai Thi Sen', 20, 'sv18@gmail.com', 8, 8.5, 8),
('SV19', 'Cao Van Tai', 21, 'sv19@gmail.com', 7.5, 7, 8),
('SV20', 'Huynh Thi Uyen', 19, 'sv20@gmail.com', 6.5, 6, 7),
('SV21', 'Vo Van Vinh', 22, 'sv21@gmail.com', 9, 9, 8.5),
('SV22', 'Nguyen Thi Xuyen', 20, 'sv22@gmail.com', 5, 5.5, 5),
('SV23', 'Tran Van Yen', 21, 'sv23@gmail.com', 8.5, 9, 8.5),
('SV24', 'Le Thi Dao', 19, 'sv24@gmail.com', 7, 7, 7),
('SV25', 'Pham Van Manh', 23, 'sv25@gmail.com', 6, 6.5, 6.5),
('SV26', 'Hoang Thi Nu', 20, 'sv26@gmail.com', 10, 9.5, 9.5),
('SV27', 'Dang Van Quyet', 22, 'sv27@gmail.com', 4, 4.5, 5),
('SV28', 'Bui Thi Suong', 21, 'sv28@gmail.com', 8, 7.5, 8),
('SV29', 'Do Van Thang', 20, 'sv29@gmail.com', 9.5, 9, 9.5),
('SV30', 'Ngo Thi Van', 19, 'sv30@gmail.com', 7.5, 8, 7.5),
('SV_TEST', 'Nguyen Van Test', 20, 'test@gmail.com', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL,
  `maSV` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`username`, `password`, `role`, `maSV`) VALUES
('admin', '123', 'TEACHER', NULL),
('sv01', '123', 'STUDENT', 'SV01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD PRIMARY KEY (`maSV`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
