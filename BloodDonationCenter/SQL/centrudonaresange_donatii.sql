-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: centrudonaresange
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `donatii`
--

DROP TABLE IF EXISTS `donatii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donatii` (
  `donatieID` int NOT NULL AUTO_INCREMENT,
  `donatorID` int NOT NULL,
  `data_donatie` date DEFAULT NULL,
  `cantitate` int DEFAULT NULL,
  `centruID` int NOT NULL,
  PRIMARY KEY (`donatieID`),
  KEY `FK_donatorID_donatii` (`donatorID`),
  KEY `FK_centruID_donatii` (`centruID`),
  CONSTRAINT `FK_centruID_donatii` FOREIGN KEY (`centruID`) REFERENCES `centru` (`centruID`),
  CONSTRAINT `FK_donatorID_donatii` FOREIGN KEY (`donatorID`) REFERENCES `donator` (`donatorID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donatii`
--

LOCK TABLES `donatii` WRITE;
/*!40000 ALTER TABLE `donatii` DISABLE KEYS */;
INSERT INTO `donatii` VALUES (1,1,'2022-04-01',450,1),(2,2,'2021-09-15',300,2),(3,3,'2023-01-25',500,3),(4,4,'2022-07-10',400,4),(5,2,'2023-01-03',150,1),(6,3,'2023-02-04',90,1),(7,2,'2023-01-03',150,1),(8,4,'2023-01-03',150,1),(9,1,'2023-01-03',150,1),(10,1,'2023-01-03',150,1),(11,3,'2023-02-04',90,1),(12,3,'2023-02-04',300,1),(13,3,'2023-02-04',300,1),(14,1,'2023-01-03',150,1),(15,1,'2023-12-20',150,1),(16,9,'2023-12-23',199,2),(17,9,'2023-12-22',170,4),(18,1,'2023-12-21',200,1),(19,1,'2023-12-21',100,1),(20,1,'2023-12-29',150,6),(21,1,'2023-12-29',100,6),(22,1,'2023-12-29',1,1),(23,1,'2023-12-29',200,4),(35,1,'2023-12-29',100,5),(36,1,'2023-12-29',100,1),(37,1,'2023-12-29',100,2),(39,1,'2023-12-29',100,4),(40,1,'2023-12-29',100,5),(42,1,'2023-12-29',200,3),(43,1,'2024-01-17',100,1);
/*!40000 ALTER TABLE `donatii` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `UpdateMaxDonator` AFTER INSERT ON `donatii` FOR EACH ROW BEGIN
    DECLARE total_donat INT;
    SELECT SUM(cantitate) INTO total_donat FROM donatii WHERE donatorID = NEW.donatorID;
    INSERT INTO max_donator (donatorID, cantitate_donata)
    VALUES (NEW.donatorID, total_donat)
    ON DUPLICATE KEY UPDATE cantitate_donata = total_donat;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_donatie_update_stock` AFTER INSERT ON `donatii` FOR EACH ROW BEGIN
    DECLARE stoc_existenta INT;
    
    SELECT cantitate INTO stoc_existenta
    FROM stock
    WHERE grupa_sange = (SELECT grupa_sange FROM info_donator WHERE donatorID = NEW.donatorID) AND centruID = NEW.centruID;

    IF stoc_existenta IS NULL THEN
        INSERT INTO stock (grupa_sange, cantitate, centruID)
        VALUES ((SELECT grupa_sange FROM info_donator WHERE donatorID = NEW.donatorID), NEW.cantitate, NEW.centruID);
    ELSE
        UPDATE stock
        SET cantitate = stoc_existenta + NEW.cantitate
        WHERE grupa_sange = (SELECT grupa_sange FROM info_donator WHERE donatorID = NEW.donatorID)
        AND centruID = NEW.centruID;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 21:33:57
