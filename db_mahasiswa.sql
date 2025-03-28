-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 28, 2025 at 06:16 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int NOT NULL,
  `nim` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nama` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `jenis_kelamin` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nilai` enum('A','B','C','D','E') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'E'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `jenis_kelamin`, `nilai`) VALUES
(1, '2203999', 'Amelia Zalfa Julianti', 'Perempuan', 'A'),
(2, '2202292', 'Muhammad Iqbal Fadhilah', 'Laki-laki', 'B'),
(3, '2202346', 'Muhammad Rifky Afandi', 'Laki-laki', 'C'),
(4, '2210239', 'Muhammad Hanif Abdillah', 'Laki-laki', 'B'),
(5, '2202046', 'Nurainun', 'Perempuan', 'A'),
(6, '2205101', 'Kelvin Julian Putra', 'Laki-laki', 'B'),
(7, '2200163', 'Rifanny Lysara Annastasya', 'Perempuan', 'A'),
(8, '2202869', 'Revana Faliha Salma', 'Perempuan', 'B'),
(9, '2209489', 'Rakha Dhifiargo Hariadi', 'Laki-laki', 'A'),
(10, '2203142', 'Roshan Syalwan Nurilham', 'Laki-laki', 'C'),
(11, '2200311', 'Raden Rahman Ismail', 'Laki-laki', 'E'),
(12, '2200978', 'Ratu Syahirah Khairunnisa', 'Perempuan', 'A'),
(13, '2204509', 'Muhammad Fahreza Fauzan', 'Laki-laki', 'B'),
(14, '2205027', 'Muhammad Rizki Revandi', 'Laki-laki', 'D'),
(15, '2203484', 'Arya Aydin Margono', 'Laki-laki', 'C'),
(16, '2200481', 'Marvel Ravindra Dioputra', 'Laki-laki', 'B'),
(17, '2209889', 'Muhammad Fadlul Hafiizh', 'Laki-laki', 'D'),
(18, '2206697', 'Rifa Sania', 'Perempuan', 'A'),
(19, '2207260', 'Imam Chalish Rafidhul Haque', 'Laki-laki', 'B'),
(20, '2204343', 'Meiva Labibah Putri', 'Perempuan', 'A');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
