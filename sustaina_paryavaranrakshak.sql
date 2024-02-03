-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 01, 2024 at 05:23 PM
-- Server version: 8.0.35
-- PHP Version: 8.1.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sustaina_paryavaranrakshak`
--

-- --------------------------------------------------------

--
-- Table structure for table `buyer`
--

CREATE TABLE `buyer` (
  `id` int NOT NULL,
  `uid` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `gstn` varchar(200) NOT NULL,
  `email` varchar(300) NOT NULL,
  `contactNumber` varchar(11) NOT NULL,
  `address` varchar(500) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `longitude` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `latitude` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `buyer`
--

INSERT INTO `buyer` (`id`, `uid`, `name`, `gstn`, `email`, `contactNumber`, `address`, `city`, `state`, `longitude`, `latitude`) VALUES
(1, 'IE45sFO3yPOnsg7zaoa9IUMVYO22', 'test', 'test5dgl', 'test@gmail.com', '1234567890', 'test', 'test', 'test', NULL, NULL),
(2, '4c97MCkTsHcdUx4TJlz5nUQhoJS2', 'Manav Patni', '616272iiw', 'developer.mnvpatni@gmail.com', '1233456789', 'test', 'test', 'test', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int NOT NULL,
  `name` varchar(500) NOT NULL,
  `imagelink` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category` varchar(500) NOT NULL,
  `sub_category` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL,
  `quantity` int NOT NULL,
  `price` float NOT NULL,
  `sellerUID` varchar(200) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `imagelink`, `category`, `sub_category`, `description`, `quantity`, `price`, `sellerUID`, `city`, `state`) VALUES
(1, 'twst', 'https://img.freepik.com/premium-photo/modern-smartphone-with-large-broken-screen-with-debris-grunge-backgdound-with-copy-space_222464-1567.jpg', 'Category 4', 'Category 3', 'tsts', 55, 124, 'IE45sFO3yPOnsg7zaoa9IUMVYO22', 'test', 'tstst'),
(2, 'Testidue', 'https://firebasestorage.googleapis.com/v0/b/sustainable-sathi.appspot.com/o/productsImg%2FThu%20Feb%2001%2021%3A48%3A31%20GMT%2B05%3A30%202024?alt=media&token=e8471b63-d0a1-4ed3-ad09-f95c039b11da', 'Category 4', 'Category 3', 'Theus is sia o', 3, 2800, 'IE45sFO3yPOnsg7zaoa9IUMVYO22', 'test', 'test'),
(3, 'Laptop', 'https://firebasestorage.googleapis.com/v0/b/sustainable-sathi.appspot.com/o/productsImg%2FThu%20Feb%2001%2021%3A48%3A31%20GMT%2B05%3A30%202024%2FThu%20Feb%2001%2021%3A49%3A02%20GMT%2B05%3A30%202024?alt=media&token=a4bdac7b-bdde-459d-a01e-f25dc03f6116', 'Category 2', 'Category 1', 'Theus is sia o', 1, 28000, 'IE45sFO3yPOnsg7zaoa9IUMVYO22', 'test', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `seller`
--

CREATE TABLE `seller` (
  `id` int NOT NULL,
  `uid` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `age` int NOT NULL,
  `email` varchar(300) NOT NULL,
  `contactNumber` varchar(11) NOT NULL,
  `address` varchar(500) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `longitude` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `latitude` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `seller`
--

INSERT INTO `seller` (`id`, `uid`, `name`, `age`, `email`, `contactNumber`, `address`, `city`, `state`, `longitude`, `latitude`) VALUES
(1, 'IE45sFO3yPOnsg7zaoa9IUMVYO22', 'Manav Patni', 23, 'mnvpatni@gmail.com', '1234567890', 'test ', 'test', 'test', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buyer`
--
ALTER TABLE `buyer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seller`
--
ALTER TABLE `seller`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buyer`
--
ALTER TABLE `buyer`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `seller`
--
ALTER TABLE `seller`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
