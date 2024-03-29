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
-- Table structure for table `cereri`
--

DROP TABLE IF EXISTS `cereri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cereri` (
  `cerereID` int NOT NULL AUTO_INCREMENT,
  `primitorID` int NOT NULL,
  `data_cerere` date DEFAULT NULL,
  `cantitate` int DEFAULT NULL,
  `centruID` int NOT NULL,
  PRIMARY KEY (`cerereID`),
  KEY `FK_primitorID_cereri` (`primitorID`),
  KEY `FK_centruID_cereri` (`centruID`),
  CONSTRAINT `FK_centruID_cereri` FOREIGN KEY (`centruID`) REFERENCES `centru` (`centruID`),
  CONSTRAINT `FK_primitorID_cereri` FOREIGN KEY (`primitorID`) REFERENCES `primitor` (`primitorID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cereri`
--

LOCK TABLES `cereri` WRITE;
/*!40000 ALTER TABLE `cereri` DISABLE KEYS */;
INSERT INTO `cereri` VALUES (1,3,'2022-05-10',350,1),(2,4,'2021-10-25',250,2),(3,5,'2023-02-05',450,3),(4,6,'2022-08-20',300,4),(5,1,'2023-01-02',500,1),(6,1,'2023-01-02',500,1),(7,8,'2023-12-20',100,2),(8,8,'2023-12-20',100,1),(9,1,'2023-12-22',200,1),(10,9,'2023-12-29',200,6),(11,8,'2023-12-29',10,1),(13,8,'2023-12-29',1,5),(15,8,'2023-12-29',1,4),(16,8,'2023-12-29',1,6),(18,1,'2023-01-02',500,1),(19,1,'2023-01-02',500,1),(21,1,'2023-01-02',500,3),(22,8,'2023-12-29',1,3);
/*!40000 ALTER TABLE `cereri` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_cerere_update_stock` AFTER INSERT ON `cereri` FOR EACH ROW BEGIN
    DECLARE stoc_existenta INT;
    SELECT cantitate INTO stoc_existenta
    FROM stock
    WHERE grupa_sange = (SELECT grupa_sange FROM info_primitor WHERE primitorID = NEW.primitorID) AND centruID = NEW.centruID;

    IF stoc_existenta IS NOT NULL THEN
        UPDATE stock
        SET cantitate = stoc_existenta - NEW.cantitate
        WHERE grupa_sange = (SELECT grupa_sange FROM info_primitor WHERE primitorID = NEW.primitorID) AND centruID = NEW.centruID;
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

-- Dump completed on 2024-03-29 21:33:56
