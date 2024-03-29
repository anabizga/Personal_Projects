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
-- Table structure for table `info_primitor`
--

DROP TABLE IF EXISTS `info_primitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_primitor` (
  `primitorID` int NOT NULL,
  `nume` varchar(20) DEFAULT NULL,
  `prenume` varchar(20) DEFAULT NULL,
  `cnp` varchar(25) DEFAULT NULL,
  `gen` varchar(2) DEFAULT NULL,
  `grupa_sange` varchar(3) DEFAULT NULL,
  KEY `FK_primitorID_info_primitor` (`primitorID`),
  CONSTRAINT `FK_primitorID_info_primitor` FOREIGN KEY (`primitorID`) REFERENCES `primitor` (`primitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_primitor`
--

LOCK TABLES `info_primitor` WRITE;
/*!40000 ALTER TABLE `info_primitor` DISABLE KEYS */;
INSERT INTO `info_primitor` VALUES (1,'Alex','Smith','9012345678901','M','A+'),(2,'Emma','Davis','0123456789012','F','B-'),(3,'Daniel','Wilson','9012345678901','M','A+'),(4,'Sophia','Smith','0123456789012','F','B-'),(5,'Michael','Johnson','1234567890123','M','AB+'),(6,'Olivia','Davis','2345678901234','F','O-'),(7,'Monica','Juravle','1234567890123','F','A+'),(8,'bizga','ana','6040429184757','F','A-'),(9,'Juravle','Monica','187874590783','F','O-');
/*!40000 ALTER TABLE `info_primitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 21:33:57
