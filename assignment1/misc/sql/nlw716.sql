-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: devcloud.fulgentcorp.com    Database: nlw716
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `all_parts`
--

DROP TABLE IF EXISTS `all_parts`;
/*!50001 DROP VIEW IF EXISTS `all_parts`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `all_parts` AS SELECT 
 1 AS `pid`,
 1 AS `part_number`,
 1 AS `part_name`,
 1 AS `vendor_name`,
 1 AS `extern_part_number`,
 1 AS `quantity_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parts_id` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `locations_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `part_locations_unique1` (`parts_id`,`locations_id`),
  KEY `locations_id_fkey1_idx` (`locations_id`),
  KEY `part_id_fkey1_idx` (`parts_id`),
  CONSTRAINT `locations_id_fkey1` FOREIGN KEY (`locations_id`) REFERENCES `locations` (`pid`) ON UPDATE NO ACTION,
  CONSTRAINT `part_id_fkey1` FOREIGN KEY (`parts_id`) REFERENCES `parts` (`pid`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locations` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `location_name` varchar(45) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'Facility 1 Warehouse 1'),(2,'Facility 1 Warehouse 2'),(3,'Facility 2'),(4,'Unknown');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parts`
--

DROP TABLE IF EXISTS `parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parts` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `part_number` varchar(63) NOT NULL,
  `part_name` varchar(255) NOT NULL,
  `vendor_id` int(10) unsigned DEFAULT NULL,
  `extern_part_number` varchar(63) NOT NULL,
  `unit_of_quantities_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `part_number_UNIQUE` (`part_number`),
  KEY `vendor_id_fk1_idx` (`vendor_id`),
  KEY `unit_of_quantities_fk1_idx` (`unit_of_quantities_id`),
  CONSTRAINT `unit_of_quantities_fk1` FOREIGN KEY (`unit_of_quantities_id`) REFERENCES `unit_of_quantities` (`pid`) ON UPDATE NO ACTION,
  CONSTRAINT `vendor_id_fk1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`pid`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parts`
--

LOCK TABLES `parts` WRITE;
/*!40000 ALTER TABLE `parts` DISABLE KEYS */;
INSERT INTO `parts` VALUES (2,'EVA001','Eva Unit 1',1,'EVO1',1),(3,'EVA002','Eva Unit 2',1,'EVO2',1),(4,'EVA003','Eva Unit 3',1,'EVO3',1);
/*!40000 ALTER TABLE `parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_of_quantities`
--

DROP TABLE IF EXISTS `unit_of_quantities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit_of_quantities` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `quantity_name` varchar(63) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `quantity_name_UNIQUE` (`quantity_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_of_quantities`
--

LOCK TABLES `unit_of_quantities` WRITE;
/*!40000 ALTER TABLE `unit_of_quantities` DISABLE KEYS */;
INSERT INTO `unit_of_quantities` VALUES (1,'Linear Feet'),(2,'Pieces'),(3,'Unknown');
/*!40000 ALTER TABLE `unit_of_quantities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendors`
--

DROP TABLE IF EXISTS `vendors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendors` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vendor_name` varchar(256) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `vendor_name_UNIQUE` (`vendor_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendors`
--

LOCK TABLES `vendors` WRITE;
/*!40000 ALTER TABLE `vendors` DISABLE KEYS */;
INSERT INTO `vendors` VALUES (1,'NERV1'),(2,'NERV2'),(3,'NERV3');
/*!40000 ALTER TABLE `vendors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `all_parts`
--

/*!50001 DROP VIEW IF EXISTS `all_parts`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`nlw716`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `all_parts` AS select `prt`.`pid` AS `pid`,`prt`.`part_number` AS `part_number`,`prt`.`part_name` AS `part_name`,`ven`.`vendor_name` AS `vendor_name`,`prt`.`extern_part_number` AS `extern_part_number`,`uoq`.`quantity_name` AS `quantity_name` from ((`parts` `prt` join `vendors` `ven` on((`ven`.`pid` = `prt`.`vendor_id`))) join `unit_of_quantities` `uoq` on((`uoq`.`pid` = `prt`.`unit_of_quantities_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-24 22:23:01
