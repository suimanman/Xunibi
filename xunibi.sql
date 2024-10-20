/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : xunibi

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 20/10/2024 14:18:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Achievements
-- ----------------------------
DROP TABLE IF EXISTS `Achievements`;
CREATE TABLE `Achievements` (
  `achievement_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `achievement_type` varchar(50) NOT NULL,
  `achievement_date` date NOT NULL,
  `coin_awarded` decimal(10,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`achievement_id`),
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `achievements_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`),
  CONSTRAINT `achievements_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Achievements
-- ----------------------------
BEGIN;
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`) VALUES (1, 1, 1, 'paper', '2023-09-20', 50.00, 'Published a paper on AI innovation');
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`) VALUES (2, 2, 3, 'patent', '2023-08-15', 100.00, 'Filed a patent on new tech innovation');
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`) VALUES (3, 3, 4, 'competition', '2023-07-05', 75.00, 'Won first prize in tech competition');
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`) VALUES (4, 1, 1, 'paper', '2024-10-19', 100.00, 'test');
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`) VALUES (5, 1, 1, 'paper', '2024-10-19', 100.00, 'test');
COMMIT;

-- ----------------------------
-- Table structure for Area
-- ----------------------------
DROP TABLE IF EXISTS `Area`;
CREATE TABLE `Area` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `rented_team_id` int DEFAULT NULL,
  `coin_consumption` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team4` (`rented_team_id`),
  CONSTRAINT `fk_team4` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Area
-- ----------------------------
BEGIN;
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (1, 'Conference Room', 0, 1, 200);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (2, 'Event Space', 1, NULL, 300);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (3, 'Workshop Area', 0, 3, 150);
COMMIT;

-- ----------------------------
-- Table structure for Camera
-- ----------------------------
DROP TABLE IF EXISTS `Camera`;
CREATE TABLE `Camera` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `rented_team_id` int DEFAULT NULL,
  `coin_consumption` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team3` (`rented_team_id`),
  CONSTRAINT `fk_team3` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Camera
-- ----------------------------
BEGIN;
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (1, 'DSLR Camera', 1, NULL, 80);
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (2, 'Video Camera', 0, 1, 100);
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (3, 'Action Camera', 1, NULL, 60);
COMMIT;

-- ----------------------------
-- Table structure for DeviceUsageRecords
-- ----------------------------
DROP TABLE IF EXISTS `DeviceUsageRecords`;
CREATE TABLE `DeviceUsageRecords` (
  `usage_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `device_name` varchar(100) NOT NULL,
  `usage_date` date NOT NULL,
  `virtual_coin_spent` decimal(10,2) NOT NULL,
  PRIMARY KEY (`usage_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `deviceusagerecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of DeviceUsageRecords
-- ----------------------------
BEGIN;
INSERT INTO `DeviceUsageRecords` (`usage_id`, `team_id`, `device_name`, `usage_date`, `virtual_coin_spent`) VALUES (1, 1, '3D Printer', '2023-09-30', 30.00);
INSERT INTO `DeviceUsageRecords` (`usage_id`, `team_id`, `device_name`, `usage_date`, `virtual_coin_spent`) VALUES (2, 2, 'Laser Cutter', '2023-09-30', 50.00);
INSERT INTO `DeviceUsageRecords` (`usage_id`, `team_id`, `device_name`, `usage_date`, `virtual_coin_spent`) VALUES (3, 3, 'Camera', '2023-09-30', 20.00);
COMMIT;

-- ----------------------------
-- Table structure for DutyRecords
-- ----------------------------
DROP TABLE IF EXISTS `DutyRecords`;
CREATE TABLE `DutyRecords` (
  `duty_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `duty_date` date NOT NULL,
  `coin_awarded` decimal(10,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`duty_id`),
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `dutyrecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`),
  CONSTRAINT `dutyrecords_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of DutyRecords
-- ----------------------------
BEGIN;
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (1, 1, 2, '2023-09-28', 20.00, 'Team duty on project maintenance');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (2, 2, 3, '2023-09-28', 20.00, 'Team duty on workshop preparation');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (3, 3, 4, '2023-09-28', 20.00, 'Team duty on organizing event');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (4, 1, 1, '2024-10-20', 20.00, 'test');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (5, 1, 1, '2024-10-20', 20.00, 'test');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (6, 1, 1, '2024-10-20', 20.00, 'traintest');
COMMIT;

-- ----------------------------
-- Table structure for Equipment
-- ----------------------------
DROP TABLE IF EXISTS `Equipment`;
CREATE TABLE `Equipment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `rented_team_id` int DEFAULT NULL,
  `coin_consumption` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team2` (`rented_team_id`),
  CONSTRAINT `fk_team2` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Equipment
-- ----------------------------
BEGIN;
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (1, '3D Printer', 0, 3, 120);
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (2, 'Laser Cutter', 1, NULL, 150);
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (3, 'CNC Machine', 0, 2, 180);
COMMIT;

-- ----------------------------
-- Table structure for RentalRecords
-- ----------------------------
DROP TABLE IF EXISTS `RentalRecords`;
CREATE TABLE `RentalRecords` (
  `rental_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `rental_date` date NOT NULL,
  `virtual_coin_spent` decimal(10,2) NOT NULL,
  `rental_duration_days` int NOT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `rentalrecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of RentalRecords
-- ----------------------------
BEGIN;
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `virtual_coin_spent`, `rental_duration_days`) VALUES (1, 1, '2023-09-15', 200.00, 30);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `virtual_coin_spent`, `rental_duration_days`) VALUES (2, 2, '2023-09-10', 150.00, 20);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `virtual_coin_spent`, `rental_duration_days`) VALUES (3, 3, '2023-09-05', 100.00, 10);
COMMIT;

-- ----------------------------
-- Table structure for SignInRecords
-- ----------------------------
DROP TABLE IF EXISTS `SignInRecords`;
CREATE TABLE `SignInRecords` (
  `sign_in_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `sign_in_date` date NOT NULL,
  `virtual_coin_awarded` decimal(10,2) NOT NULL,
  PRIMARY KEY (`sign_in_id`),
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `signinrecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`),
  CONSTRAINT `signinrecords_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of SignInRecords
-- ----------------------------
BEGIN;
INSERT INTO `SignInRecords` (`sign_in_id`, `team_id`, `user_id`, `sign_in_date`, `virtual_coin_awarded`) VALUES (1, 1, 1, '2023-10-01', 10.00);
INSERT INTO `SignInRecords` (`sign_in_id`, `team_id`, `user_id`, `sign_in_date`, `virtual_coin_awarded`) VALUES (2, 1, 2, '2023-10-01', 10.00);
INSERT INTO `SignInRecords` (`sign_in_id`, `team_id`, `user_id`, `sign_in_date`, `virtual_coin_awarded`) VALUES (3, 2, 3, '2023-10-01', 10.00);
INSERT INTO `SignInRecords` (`sign_in_id`, `team_id`, `user_id`, `sign_in_date`, `virtual_coin_awarded`) VALUES (4, 3, 4, '2023-10-01', 10.00);
COMMIT;

-- ----------------------------
-- Table structure for Team
-- ----------------------------
DROP TABLE IF EXISTS `Team`;
CREATE TABLE `Team` (
  `team_id` int NOT NULL AUTO_INCREMENT,
  `team_name` varchar(100) NOT NULL,
  `creation_date` date NOT NULL,
  `virtual_coins` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Team
-- ----------------------------
BEGIN;
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (1, 'hhh', '2023-01-10', 1270.00);
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (2, 'Tech Pioneers', '2023-03-15', 500.00);
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (3, 'Creative Minds', '2023-05-20', 750.00);
COMMIT;

-- ----------------------------
-- Table structure for trainRecords
-- ----------------------------
DROP TABLE IF EXISTS `trainRecords`;
CREATE TABLE `trainRecords` (
  `train_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int NOT NULL,
  `user_id` int NOT NULL,
  `event_name` varchar(255) NOT NULL,
  `event_date` date NOT NULL,
  `event_type` varchar(50) NOT NULL,
  `coins` decimal(10,2) NOT NULL,
  `description` text,
  PRIMARY KEY (`train_id`) USING BTREE,
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `trainrecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `trainrecords_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of trainRecords
-- ----------------------------
BEGIN;
INSERT INTO `trainRecords` (`train_id`, `team_id`, `user_id`, `event_name`, `event_date`, `event_type`, `coins`, `description`) VALUES (5, 1, 1, '技术培训会', '2024-10-10', '培训', 50.00, '团队成员参加了技术培训，获得虚拟币');
INSERT INTO `trainRecords` (`train_id`, `team_id`, `user_id`, `event_name`, `event_date`, `event_type`, `coins`, `description`) VALUES (6, 2, 3, '项目启动会议', '2024-10-12', '会议', 30.00, '团队成员参与了项目启动会议');
INSERT INTO `trainRecords` (`train_id`, `team_id`, `user_id`, `event_name`, `event_date`, `event_type`, `coins`, `description`) VALUES (7, 1, 2, '管理技能培训', '2024-10-15', '培训', 40.00, '管理技能提升课程，获得虚拟币');
INSERT INTO `trainRecords` (`train_id`, `team_id`, `user_id`, `event_name`, `event_date`, `event_type`, `coins`, `description`) VALUES (8, 3, 4, '年度总结会议', '2024-10-20', '会议', 60.00, '年度总结会议后获得奖励');
INSERT INTO `trainRecords` (`train_id`, `team_id`, `user_id`, `event_name`, `event_date`, `event_type`, `coins`, `description`) VALUES (9, 1, 1, '技术培训会', '2024-10-19', '校内培训', 50.00, 'traintest');
COMMIT;

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `role` varchar(50) NOT NULL,
  `registration_date` date NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of User
-- ----------------------------
BEGIN;
INSERT INTO `User` (`user_id`, `team_id`, `user_name`, `password`, `email`, `role`, `registration_date`) VALUES (1, 1, 'Alice', 'password123', 'alice@example.com', 'leader', '2023-01-11');
INSERT INTO `User` (`user_id`, `team_id`, `user_name`, `password`, `email`, `role`, `registration_date`) VALUES (2, 1, 'Bob', 'password456', 'bob@example.com', 'member', '2023-01-12');
INSERT INTO `User` (`user_id`, `team_id`, `user_name`, `password`, `email`, `role`, `registration_date`) VALUES (3, 2, 'Charlie', 'password789', 'charlie@example.com', 'leader', '2023-03-16');
INSERT INTO `User` (`user_id`, `team_id`, `user_name`, `password`, `email`, `role`, `registration_date`) VALUES (4, 3, 'David', 'password101', 'david@example.com', 'leader', '2023-05-21');
COMMIT;

-- ----------------------------
-- Table structure for VirtualCoinTransactions
-- ----------------------------
DROP TABLE IF EXISTS `VirtualCoinTransactions`;
CREATE TABLE `VirtualCoinTransactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `transaction_date` date NOT NULL,
  `transaction_type` varchar(50) NOT NULL,
  `virtual_coin_amount` decimal(10,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `virtualcointransactions_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of VirtualCoinTransactions
-- ----------------------------
BEGIN;
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `virtual_coin_amount`, `description`) VALUES (1, 1, '2023-10-01', 'gain', 10.00, 'Daily sign-in reward');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `virtual_coin_amount`, `description`) VALUES (2, 2, '2023-10-01', 'spend', 50.00, 'Laser Cutter usage');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `virtual_coin_amount`, `description`) VALUES (3, 3, '2023-10-01', 'gain', 20.00, 'Duty work reward');
COMMIT;

-- ----------------------------
-- Table structure for Workstation
-- ----------------------------
DROP TABLE IF EXISTS `Workstation`;
CREATE TABLE `Workstation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `rented_team_id` int DEFAULT NULL,
  `coin_consumption` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team` (`rented_team_id`),
  CONSTRAINT `fk_team` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Workstation
-- ----------------------------
BEGIN;
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (1, 'Private Office', 0, 1, 100);
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (2, 'Shared Desk', 1, NULL, 50);
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`) VALUES (3, 'Dedicated Desk', 0, 2, 75);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
