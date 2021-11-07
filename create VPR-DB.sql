-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               10.6.4-MariaDB - mariadb.org binary distribution
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Exportiere Datenbank Struktur für vpr
CREATE DATABASE IF NOT EXISTS `vpr` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE `vpr`;

-- Exportiere Struktur von Tabelle vpr.event
CREATE TABLE IF NOT EXISTS `event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '0',
  `priority` tinyint(2) unsigned NOT NULL DEFAULT 0,
  `is_full_day` bit(1) NOT NULL DEFAULT b'0',
  `start` time DEFAULT NULL,
  `end` time DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Exportiere Daten aus Tabelle vpr.event: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` (`id`, `name`, `priority`, `is_full_day`, `start`, `end`) VALUES
	(1, 'TestEvent', 0, b'0', '00:00:00', '00:00:00'),
	(2, 'Event 2', 1, b'0', NULL, NULL);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle vpr.event_list
CREATE TABLE IF NOT EXISTS `event_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `event_id` int(10) unsigned NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UID` (`user_id`) USING BTREE,
  KEY `EID` (`event_id`) USING BTREE,
  CONSTRAINT `event_id` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Exportiere Daten aus Tabelle vpr.event_list: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `event_list` DISABLE KEYS */;
INSERT INTO `event_list` (`id`, `user_id`, `event_id`, `date`) VALUES
	(1, 1, 2, '2021-11-11'),
	(2, 2, 1, '2021-12-24');
/*!40000 ALTER TABLE `event_list` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle vpr.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Exportiere Daten aus Tabelle vpr.hibernate_sequence: ~1 rows (ungefähr)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(3);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle vpr.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '0',
  `forename` varchar(20) NOT NULL DEFAULT '0',
  `password` varchar(50) NOT NULL DEFAULT '0',
  `token` varchar(50) DEFAULT '0',
  `is_admin` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Exportiere Daten aus Tabelle vpr.user: ~3 rows (ungefähr)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`, `forename`, `password`, `token`, `is_admin`) VALUES
	(1, 'Beyer', 'Marc', 'test', 'test', b'1'),
	(2, 'Kühn', 'Marco', 'test', 'test', b'0'),
	(3, 'Rechtin', 'Alex', 'test', 'test', b'1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
