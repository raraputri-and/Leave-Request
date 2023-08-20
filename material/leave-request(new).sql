-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.2.0.6576
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for leave_request
CREATE DATABASE IF NOT EXISTS `leave_request` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `leave_request`;

-- Dumping structure for table leave_request.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `employee_gender` varchar(255) NOT NULL,
  `employee_name` varchar(255) NOT NULL,
  `employee_nip` varchar(255) NOT NULL,
  `manager_id` int NOT NULL,
  `religion_id` int NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `UK_dgeqd9vpkin0dme95sgr3cnts` (`employee_nip`),
  KEY `FKou6wbxug1d0qf9mabut3xqblo` (`manager_id`),
  KEY `FKsmckk4enuq0ll0yq689hlmfhc` (`religion_id`),
  CONSTRAINT `FKou6wbxug1d0qf9mabut3xqblo` FOREIGN KEY (`manager_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FKsmckk4enuq0ll0yq689hlmfhc` FOREIGN KEY (`religion_id`) REFERENCES `religion` (`religion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.employee: ~0 rows (approximately)
INSERT INTO `employee` (`employee_id`, `employee_gender`, `employee_name`, `employee_nip`, `manager_id`, `religion_id`) VALUES
	(1, 'Male', 'Dummy', '11000', 1, 1),
	(2, 'Male', 'Admin', '11001', 2, 1),
	(3, 'Female', 'Rara', '11002', 3, 1),
	(4, 'Male', 'Natan', '11003', 3, 2),
	(6, 'Female', 'Bachtiar', '11004', 3, 4),
	(7, 'Male', 'vino', '11006', 3, 1);

-- Dumping structure for table leave_request.joint_leave
CREATE TABLE IF NOT EXISTS `joint_leave` (
  `joint_leave_id` int NOT NULL AUTO_INCREMENT,
  `joint_leave_date` datetime(6) NOT NULL,
  `is_holiday` bit(1) DEFAULT NULL,
  `joint_leave_name` varchar(255) NOT NULL,
  PRIMARY KEY (`joint_leave_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.joint_leave: ~0 rows (approximately)
INSERT INTO `joint_leave` (`joint_leave_id`, `joint_leave_date`, `is_holiday`, `joint_leave_name`) VALUES
	(2, '2023-12-25 00:00:00.000000', b'1', 'Hari Raya Natal'),
	(3, '2023-12-16 00:00:00.000000', b'0', 'Hari Saraswati'),
	(4, '2023-09-28 00:00:00.000000', b'1', 'Maulid Nabi Muhammad SAW'),
	(5, '2023-08-17 00:00:00.000000', b'1', 'Hari Proklamasi Kemerdekaan RI'),
	(6, '2023-08-12 00:00:00.000000', b'0', 'Hari Raya Kuningan'),
	(7, '2023-08-03 00:00:00.000000', b'0', 'Umanis Galungan'),
	(8, '2023-08-02 00:00:00.000000', b'0', 'Hari Raya Galungan'),
	(9, '2023-08-01 00:00:00.000000', b'0', 'Penampahan Galungan'),
	(10, '2023-07-19 00:00:00.000000', b'1', 'Tahun Baru Islam 1445 Hijriyah'),
	(11, '2023-06-29 00:00:00.000000', b'1', 'Hari Raya Idul Adha 1444 Hijriyah'),
	(12, '2023-06-04 00:00:00.000000', b'1', 'Hari Raya Waisak 2567'),
	(13, '2023-06-01 00:00:00.000000', b'1', 'Hari Lahirnya Pancasila'),
	(14, '2023-05-20 00:00:00.000000', b'0', 'Hari Saraswati'),
	(15, '2023-05-18 00:00:00.000000', b'1', 'Kenaikan Isa Al Masih'),
	(16, '2023-05-01 00:00:00.000000', b'1', 'Hari Buruh Internasional'),
	(17, '2023-04-23 00:00:00.000000', b'1', 'Hari Raya Idul Fitri 1444 Hijriyah'),
	(18, '2023-04-22 00:00:00.000000', b'1', 'Hari Raya Idul Fitri 1444 Hijriyah'),
	(19, '2023-04-07 00:00:00.000000', b'1', 'Wafat Isa Al Masih'),
	(20, '2023-03-22 00:00:00.000000', b'1', 'Hari Raya Nyepi'),
	(21, '2023-02-18 00:00:00.000000', b'1', 'Isra Mikraj Nabi Muhammad SAW'),
	(22, '2023-01-22 00:00:00.000000', b'1', 'Tahun Baru Imlek 2574 Kongzili'),
	(23, '2023-01-20 00:00:00.000000', b'0', 'Hari Siwa Ratri'),
	(24, '2023-01-14 00:00:00.000000', b'0', 'Hari Raya Kuningan'),
	(25, '2023-01-05 00:00:00.000000', b'0', 'Umanis Galungan'),
	(26, '2023-01-04 00:00:00.000000', b'0', 'Hari Raya Galungan'),
	(27, '2023-01-03 00:00:00.000000', b'0', 'Penampahan Galungan'),
	(28, '2023-01-01 00:00:00.000000', b'1', 'Tahun Baru Masehi');

-- Dumping structure for table leave_request.leave_remaining
CREATE TABLE IF NOT EXISTS `leave_remaining` (
  `leave_remaining_id` int NOT NULL,
  `past_remaining` int NOT NULL,
  `present_remaining` int NOT NULL,
  PRIMARY KEY (`leave_remaining_id`),
  CONSTRAINT `FKaxj7w5kip6p1qchuf8rqocbwo` FOREIGN KEY (`leave_remaining_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.leave_remaining: ~0 rows (approximately)
INSERT INTO `leave_remaining` (`leave_remaining_id`, `past_remaining`, `present_remaining`) VALUES
	(2, 0, 12),
	(3, 0, 12),
	(4, 0, 6),
	(6, 0, 12),
	(7, 0, 12);

-- Dumping structure for table leave_request.leave_request
CREATE TABLE IF NOT EXISTS `leave_request` (
  `leave_request_id` int NOT NULL AUTO_INCREMENT,
  `attachment` varchar(255) DEFAULT NULL,
  `date_end` datetime(6) NOT NULL,
  `date_start` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `employee_id` int NOT NULL,
  `leave_type_id` int NOT NULL,
  `status_action_id` int NOT NULL,
  PRIMARY KEY (`leave_request_id`),
  KEY `FKtc4wao0ica39vusknbo96ln2w` (`employee_id`),
  KEY `FKbsy0iudb8fxpkpoat8bjr29xl` (`leave_type_id`),
  KEY `FKoc2af8i3oycwhjtql6t5m9lls` (`status_action_id`),
  CONSTRAINT `FKbsy0iudb8fxpkpoat8bjr29xl` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_type` (`leave_type_id`),
  CONSTRAINT `FKoc2af8i3oycwhjtql6t5m9lls` FOREIGN KEY (`status_action_id`) REFERENCES `status_action` (`status_action_id`),
  CONSTRAINT `FKtc4wao0ica39vusknbo96ln2w` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.leave_request: ~0 rows (approximately)
INSERT INTO `leave_request` (`leave_request_id`, `attachment`, `date_end`, `date_start`, `quantity`, `reason`, `employee_id`, `leave_type_id`, `status_action_id`) VALUES
	(1, '', '2023-08-15 00:00:00.000000', '2023-08-15 00:00:00.000000', 1, 'Saya Izin Cuti Wisuda', 4, 1, 1),
	(2, '', '2023-09-06 00:00:00.000000', '2023-09-04 00:00:00.000000', 3, 'Saya ingin Nikah', 4, 2, 3),
	(3, '', '2023-08-31 00:00:00.000000', '2023-08-31 00:00:00.000000', 1, 'Baptisan Anak', 4, 6, 1),
	(4, '', '2023-10-20 00:00:00.000000', '2023-08-14 00:00:00.000000', 50, 'Cuti Melahirkan', 3, 4, 1),
	(5, '', '2024-01-30 00:00:00.000000', '2024-01-18 00:00:00.000000', 9, 'Haji', 3, 5, 3),
	(6, '', '2024-02-22 00:00:00.000000', '2024-02-21 00:00:00.000000', 2, 'Haji', 4, 1, 2),
	(7, '', '2023-12-08 00:00:00.000000', '2023-12-01 00:00:00.000000', 6, '', 4, 1, 3);

-- Dumping structure for table leave_request.leave_request_status
CREATE TABLE IF NOT EXISTS `leave_request_status` (
  `leave_request_status_id` int NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `leave_request_id` int DEFAULT NULL,
  `pic_id` int NOT NULL,
  `status_action_id` int DEFAULT NULL,
  PRIMARY KEY (`leave_request_status_id`),
  KEY `FKcubou3twmxa5qtmae5d0bcofu` (`leave_request_id`),
  KEY `FK10mbgkumeyq9tsepc8akdjwyh` (`pic_id`),
  KEY `FK9nyxg4u6oyf69wmm543h9vg1y` (`status_action_id`),
  CONSTRAINT `FK10mbgkumeyq9tsepc8akdjwyh` FOREIGN KEY (`pic_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FK9nyxg4u6oyf69wmm543h9vg1y` FOREIGN KEY (`status_action_id`) REFERENCES `status_action` (`status_action_id`),
  CONSTRAINT `FKcubou3twmxa5qtmae5d0bcofu` FOREIGN KEY (`leave_request_id`) REFERENCES `leave_request` (`leave_request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.leave_request_status: ~0 rows (approximately)
INSERT INTO `leave_request_status` (`leave_request_status_id`, `date`, `note`, `leave_request_id`, `pic_id`, `status_action_id`) VALUES
	(1, '2023-08-14 00:00:00.000000', NULL, 1, 4, 3),
	(2, '2023-08-14 00:00:00.000000', NULL, 2, 4, 3),
	(3, '2023-08-14 00:00:00.000000', NULL, 3, 4, 3),
	(4, '2023-08-14 00:00:00.000000', 'Silahkan diperbolehkan', 3, 3, 1),
	(5, '2023-08-14 00:00:00.000000', NULL, 4, 3, 3),
	(6, '2023-08-14 00:00:00.000000', 'Disetujui', 4, 3, 1),
	(7, '2023-08-14 00:00:00.000000', NULL, 5, 3, 3),
	(8, '2023-08-14 00:00:00.000000', NULL, 6, 4, 3),
	(9, '2023-08-14 00:00:00.000000', 'Tidak boleh beda agama', 6, 3, 2),
	(10, '2023-08-14 00:00:00.000000', NULL, 7, 4, 3),
	(11, '2023-08-14 00:00:00.000000', '', 1, 3, 1);

-- Dumping structure for table leave_request.leave_type
CREATE TABLE IF NOT EXISTS `leave_type` (
  `leave_type_id` int NOT NULL AUTO_INCREMENT,
  `leave_type_name` varchar(255) NOT NULL,
  `leave_type_quantity` int NOT NULL,
  `leave_type_quota` int NOT NULL,
  PRIMARY KEY (`leave_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.leave_type: ~6 rows (approximately)
INSERT INTO `leave_type` (`leave_type_id`, `leave_type_name`, `leave_type_quantity`, `leave_type_quota`) VALUES
	(1, 'Cuti Normal', 0, 0),
	(2, 'Cuti Menikah', 3, 1),
	(3, 'Cuti Keluarga Inti Meninggal', 2, 6),
	(4, 'Cuti Melahirkan', 90, 2),
	(5, 'Cuti Haji', 40, 2),
	(6, 'Cuti Baptis Anak', 1, 2);

-- Dumping structure for table leave_request.parameter
CREATE TABLE IF NOT EXISTS `parameter` (
  `parameter_id` varchar(255) NOT NULL,
  `parameter_leave_qty` varchar(255) NOT NULL,
  `parameter_note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.parameter: ~0 rows (approximately)
INSERT INTO `parameter` (`parameter_id`, `parameter_leave_qty`, `parameter_note`) VALUES
	('Max-leave', '10', '');

-- Dumping structure for table leave_request.privilege
CREATE TABLE IF NOT EXISTS `privilege` (
  `id` int NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.privilege: ~8 rows (approximately)
INSERT INTO `privilege` (`id`, `privilege_name`) VALUES
	(1, 'CREATE_EMPLOYEE'),
	(2, 'READ_EMPLOYEE'),
	(3, 'UPDATE_MANAGER'),
	(4, 'READ_MANAGER'),
	(5, 'CREATE_ADMIN'),
	(6, 'READ_ADMIN'),
	(7, 'UPDATE_ADMIN'),
	(8, 'DELETE_ADMIN'),
	(9, 'CREATE_MANAGER');

-- Dumping structure for table leave_request.religion
CREATE TABLE IF NOT EXISTS `religion` (
  `religion_id` int NOT NULL AUTO_INCREMENT,
  `religion_name` varchar(255) NOT NULL,
  PRIMARY KEY (`religion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.religion: ~5 rows (approximately)
INSERT INTO `religion` (`religion_id`, `religion_name`) VALUES
	(1, 'Islam'),
	(2, 'Kristen'),
	(3, 'Katholik'),
	(4, 'Hindu'),
	(5, 'Budha'),
	(6, 'Konghuchu');

-- Dumping structure for table leave_request.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.role: ~2 rows (approximately)
INSERT INTO `role` (`role_id`, `role_name`) VALUES
	(1, 'Employee'),
	(2, 'Manager'),
	(3, 'Admin');

-- Dumping structure for table leave_request.role_privilege
CREATE TABLE IF NOT EXISTS `role_privilege` (
  `role_id` int NOT NULL,
  `privilege_id` int NOT NULL,
  KEY `FKdkwbrwb5r8h74m1v7dqmhp99c` (`privilege_id`),
  KEY `FKsykrtrdngu5iexmbti7lu9xa` (`role_id`),
  CONSTRAINT `FKdkwbrwb5r8h74m1v7dqmhp99c` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  CONSTRAINT `FKsykrtrdngu5iexmbti7lu9xa` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.role_privilege: ~7 rows (approximately)
INSERT INTO `role_privilege` (`role_id`, `privilege_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 4),
	(2, 3),
	(3, 5),
	(3, 6),
	(3, 7),
	(3, 8),
	(2, 9);

-- Dumping structure for table leave_request.status_action
CREATE TABLE IF NOT EXISTS `status_action` (
  `status_action_id` int NOT NULL AUTO_INCREMENT,
  `status_action_color` varchar(255) NOT NULL,
  `status_action_name` varchar(255) NOT NULL,
  PRIMARY KEY (`status_action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.status_action: ~2 rows (approximately)
INSERT INTO `status_action` (`status_action_id`, `status_action_color`, `status_action_name`) VALUES
	(1, 'bg-success', 'Accepted'),
	(2, 'bg-danger', 'Rejected'),
	(3, 'bg-warning', 'Waiting for Approval');

-- Dumping structure for table leave_request.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int NOT NULL,
  `is_account_non_locked` bit(1) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_jnu1quvkutdk73q9fa4d7abe3` (`user_username`),
  CONSTRAINT `FKptw66h0vbrl880h7mhlernq8y` FOREIGN KEY (`user_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.user: ~5 rows (approximately)
INSERT INTO `user` (`user_id`, `is_account_non_locked`, `is_enabled`, `user_password`, `user_username`) VALUES
	(1, b'1', b'1', '$2a$12$euqlSpQ6JNgzFVEjRnjf7eSGqua4fPzDalu8LRrXERvopRqgZs8Vi', 'admin'),
	(2, b'1', b'1', '$2a$10$ET4/3iLUQ9FHfBQCh4RjpuEA3U9oG88wF.a7Jtjv1eNK8mS9X2IDe', 'budi'),
	(3, b'1', b'1', '$2a$10$/d6xCpYzoW6j3urVMMOuqemOYnZkmbgPHX4Q/2KFHWelPKUlo7KGq', 'rara'),
	(4, b'1', b'1', '$2a$10$rYrH669hzcUCdR9zosy7zOxXP6RlcMylJiZXcadTwj0VEHQkYdf3.', 'natan'),
	(6, b'1', b'1', '$2a$10$yWLWJVrOA6r8KcgYN.66rOVxlor14lstuuUMhFEQPCN6NxHO8enpe', 'bachtiar'),
	(7, b'1', b'1', '$2a$10$fyvDzQvsH1PRNMqEEB871e5qYP.mv9QIDlT65s9TSITPTnS5lg2jy', 'vino');

-- Dumping structure for table leave_request.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table leave_request.user_role: ~0 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 3),
	(3, 2),
	(4, 1),
	(2, 3),
	(6, 3),
	(7, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
