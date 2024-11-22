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

 Date: 22/11/2024 16:20:25
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
  `status` varchar(255) DEFAULT NULL,
  `achievement_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`achievement_id`),
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `achievements_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`),
  CONSTRAINT `achievements_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Achievements
-- ----------------------------
BEGIN;
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (1, 1, 1, 'paper', '2023-09-20', 50.00, 'Published a paper on AI innovation', NULL, NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (2, 2, 3, 'patent', '2023-08-15', 100.00, 'Filed a patent on new tech innovation', NULL, NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (3, 3, 4, 'competition', '2023-07-05', 75.00, 'Won first prize in tech competition', NULL, NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (4, 1, 1, 'paper', '2024-10-19', 100.00, 'test', NULL, NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (5, 1, 1, 'paper', '2024-10-19', 100.00, 'test', NULL, NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (8, 1, 5, '1', '2024-11-21', 0.00, '学生基本信息:\nStudentInfo(name=1, id=11, department=111, major=1, clazz=null, team=1)\n成果基本信息：\nAchievementInfo(name=1, dateValue=2024-11-21, awardUnit=1)\n申请陈述：\nStatementInfo(info=1)', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (9, 1, 5, '1', '2024-11-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：null，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-11-21，奖励单位：1\n申请陈述：\n1', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (10, 1, 5, '1', '2024-11-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：class com.example.xunibibackend.entity.dto.StudentInfo，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-11-21，奖励单位：1\n申请陈述：\n1', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (11, 1, 5, 'AI安全', '2024-09-21', 0.00, '学生基本信息:\n姓名：王梅策，学号：11，学院：人工智能与数据科学学院，专业：软件工程，班级：class com.example.xunibibackend.entity.dto.StudentInfo，所属团队：河工大\n成果基本信息：\n成果名称：AI安全，获得日期2024-09-21，奖励单位：河工大\n申请陈述：\n陈述内容', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (12, 1, 5, '1', '2024-11-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-11-21，奖励单位：1\n申请陈述：\n1', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (13, 1, 5, '1', '2024-11-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-11-21，奖励单位：1\n申请陈述：\n1', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (14, 1, 5, '1', '2024-10-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-10-21，奖励单位：1\n申请陈述：\n1', '待审核', NULL);
INSERT INTO `Achievements` (`achievement_id`, `team_id`, `user_id`, `achievement_type`, `achievement_date`, `coin_awarded`, `description`, `status`, `achievement_name`) VALUES (15, 1, 5, '论文', '2024-11-21', 0.00, '学生基本信息:\n姓名：1，学号：11，学院：1，专业：1，班级：，所属团队：1\n成果基本信息：\n成果名称：1，获得日期2024-11-21，奖励单位：1\n申请陈述：\n1', '待审核', '1');
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
  `image_url` varchar(255) DEFAULT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team4` (`rented_team_id`),
  CONSTRAINT `fk_team4` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Area
-- ----------------------------
BEGIN;
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (1, '一楼会议室-1', 1, NULL, 30, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (2, '一楼讨论室-1', 0, 3, 40, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (3, '工作台', 0, 3, 50, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (4, '一楼会议室-2', 1, NULL, 20, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (5, '一楼讨论室-2', 1, NULL, 30, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Area` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (6, '二楼会议室-1', 1, NULL, 30, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
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
  `image_url` varchar(255) DEFAULT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team3` (`rented_team_id`),
  CONSTRAINT `fk_team3` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Camera
-- ----------------------------
BEGIN;
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (1, 'DSLR 摄像机', 1, NULL, 80, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (2, 'Video摄像机', 1, NULL, 100, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Camera` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (3, 'Action 摄像机', 1, NULL, 60, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (7, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (8, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (9, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (10, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (11, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (12, 1, 1, '2024-10-22', 20.00, '值班201办公室');
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (13, 1, 1, '2024-10-25', 20.00, NULL);
INSERT INTO `DutyRecords` (`duty_id`, `team_id`, `user_id`, `duty_date`, `coin_awarded`, `description`) VALUES (14, 1, 1, '2024-10-25', 20.00, NULL);
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
  `image_url` varchar(255) DEFAULT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team2` (`rented_team_id`),
  CONSTRAINT `fk_team2` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Equipment
-- ----------------------------
BEGIN;
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (1, '3D 打印机-1', 0, 3, 120, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (2, '激光打印机', 1, NULL, 150, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Equipment` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (3, '3D 打印机-2', 0, 2, 180, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `display` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
INSERT INTO `notice` (`id`, `content`, `created_at`, `display`) VALUES (1, '下周三将举行团队培训，请务必参加！', '2024-10-31 13:48:15', 0x30);
INSERT INTO `notice` (`id`, `content`, `created_at`, `display`) VALUES (2, '系统将于今晚10点进行维护，预计1小时内完成。', '2024-10-31 13:48:15', 0x31);
INSERT INTO `notice` (`id`, `content`, `created_at`, `display`) VALUES (3, '新版本上线，增加了虚拟币交易记录功能。', '2024-10-31 13:48:15', 0x30);
INSERT INTO `notice` (`id`, `content`, `created_at`, `display`) VALUES (4, '国庆节放假安排：10月1日至10月7日，期间不提供服务。', '2024-10-31 13:48:15', 0x30);
INSERT INTO `notice` (`id`, `content`, `created_at`, `display`) VALUES (5, '团队聚餐定于本周五下午6点，地点为公司附近餐厅。', '2024-10-31 13:48:15', 0x30);
COMMIT;

-- ----------------------------
-- Table structure for RentalRecords
-- ----------------------------
DROP TABLE IF EXISTS `RentalRecords`;
CREATE TABLE `RentalRecords` (
  `rental_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `rental_date` date NOT NULL,
  `coin_spent` decimal(10,2) NOT NULL,
  `rental_days` int NOT NULL,
  `rental_type` varchar(255) DEFAULT NULL,
  `rental_or_return` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `rentalrecords_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of RentalRecords
-- ----------------------------
BEGIN;
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (1, 1, '2023-09-15', 200.00, 30, NULL, NULL);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (2, 2, '2023-09-10', 150.00, 20, NULL, NULL);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (3, 3, '2023-09-05', 100.00, 10, NULL, NULL);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (4, 1, '2024-10-21', 50.00, 1, 'Shared Desk', 0);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (5, 1, '2024-10-21', 50.00, 1, 'Shared Desk', 0);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (6, 3, '2024-10-22', 80.00, 2, 'Event Space', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (7, 1, '2024-11-13', 50.00, 1, '工位2', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (8, 1, '2024-11-13', 20.00, 1, '一楼会议室-2', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (9, 1, '2024-11-13', 80.00, 1, 'DSLR 摄像机', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (10, 1, '2024-11-13', 30.00, 1, '一楼讨论室-2', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (11, 1, '2024-11-13', 100.00, 1, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (12, 1, '2024-11-13', 100.00, 1, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (13, 1, '2024-11-13', 200.00, 2, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (14, 1, '2024-11-13', 600.00, 6, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (15, 1, '2024-11-13', 100.00, 1, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (16, 1, '2024-11-14', 200.00, 2, '工位1', 1);
INSERT INTO `RentalRecords` (`rental_id`, `team_id`, `rental_date`, `coin_spent`, `rental_days`, `rental_type`, `rental_or_return`) VALUES (17, 1, '2024-11-17', 100.00, 1, '工位1', 1);
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
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (1, '河工大众创空间', '2023-01-10', 9700.00);
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (2, '先锋者', '2023-03-15', 400.00);
INSERT INTO `Team` (`team_id`, `team_name`, `creation_date`, `virtual_coins`) VALUES (3, '暴乱头脑', '2023-05-20', 670.00);
COMMIT;

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `registration_date` date NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of User
-- ----------------------------
BEGIN;
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (1, 1, 'Alice', 'password123', 'leader', '2023-01-11');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (2, 1, 'Bob', 'password456', 'member', '2023-01-12');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (3, 2, 'Charlie', 'password789', 'leader', '2023-03-16');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (4, 3, 'David', 'password101', 'leader', '2023-05-21');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (5, 1, '11', '698d51a19d8a121ce581499d7b701668', 'leader', '2024-10-25');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (6, NULL, 'wmcc', '96e79218965eb72c92a549dd5a330112', 'leader', '2024-10-25');
INSERT INTO `User` (`user_id`, `team_id`, `username`, `password`, `role`, `registration_date`) VALUES (7, NULL, '190531', '96e79218965eb72c92a549dd5a330112', NULL, '2024-10-30');
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
  `coin_amount` decimal(10,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `virtualcointransactions_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of VirtualCoinTransactions
-- ----------------------------
BEGIN;
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (1, 1, '2023-10-01', '收入', 10.00, '签到');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (2, 1, '2023-10-01', '支出', 50.00, 'Laser Cutter usage');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (3, 1, '2023-10-01', '收入', 20.00, 'Duty work reward');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (4, 1, '2024-10-22', '收入', 20.00, '值班201办公室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (5, 1, '2024-10-22', '收入', 20.00, '值班201办公室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (6, 1, '2024-10-22', '收入', 20.00, '值班201办公室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (7, 1, '2024-10-22', '收入', 20.00, '值班201办公室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (8, 1, '2024-10-22', '收入', 20.00, '值班201办公室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (9, 1, '2024-10-22', '支出', 80.00, '租用会议室');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (10, 1, '2024-10-25', '支出', 20.00, '租用相机');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (11, 1, '2024-10-25', '支出', 30.00, '租用相机');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (12, 1, '2024-11-13', '支出', 50.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (13, 1, '2024-11-13', '支出', 20.00, '租用一楼会议室-2');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (14, 1, '2024-11-13', '支出', 80.00, '租用DSLR 摄像机');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (15, 1, '2024-11-13', '支出', 30.00, '租用一楼讨论室-2');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (16, 1, '2024-11-13', '支出', 100.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (17, 1, '2024-11-13', '支出', 100.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (18, 1, '2024-11-13', '支出', 200.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (19, 1, '2024-11-13', '支出', 600.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (20, 1, '2024-11-13', '支出', 100.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (21, 1, '2024-11-14', '支出', 200.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (22, 1, '2024-11-17', '支出', 100.00, '租用工位');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (23, 1, '2024-11-22', '支出', 800.00, '租用工位1超时4天');
INSERT INTO `VirtualCoinTransactions` (`transaction_id`, `team_id`, `transaction_date`, `transaction_type`, `coin_amount`, `description`) VALUES (24, 1, '2024-11-22', '支出', 800.00, '租用工位1超时4天');
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
  `image_url` varchar(255) DEFAULT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team` (`rented_team_id`),
  CONSTRAINT `fk_team` FOREIGN KEY (`rented_team_id`) REFERENCES `Team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Workstation
-- ----------------------------
BEGIN;
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (1, '工位1', 1, NULL, 100, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (2, '工位2', 1, NULL, 50, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
INSERT INTO `Workstation` (`id`, `type`, `is_available`, `rented_team_id`, `coin_consumption`, `image_url`, `rental_date`, `return_date`) VALUES (3, '工位3', 0, 2, 75, 'https://cdn.uviewui.com/uview/album/1.jpg', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
