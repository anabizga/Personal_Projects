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
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `grupa_sange` varchar(3) DEFAULT NULL,
  `cantitate` int DEFAULT NULL,
  `centruID` int NOT NULL,
  KEY `FK_centruID_stock` (`centruID`),
  CONSTRAINT `FK_centruID_stock` FOREIGN KEY (`centruID`) REFERENCES `centru` (`centruID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES ('A+',450,1),('A-',500,1),('B+',720,1),('B-',100,1),('AB+',140,1),('AB-',1000,1),('O+',675,1),('O-',345,1),('A+',800,2),('A-',500,2),('B+',400,2),('B-',90,2),('AB+',145,2),('AB-',100,2),('O+',35,2),('O-',345,2),('A+',-10,3),('A-',1999,3),('B+',670,3),('B-',900,3),('AB+',145,3),('AB-',610,3),('O+',350,3),('O-',10,3),('A+',80,4),('A-',540,4),('B+',470,4),('B-',95,4),('AB+',175,4),('AB-',90,4),('O+',350,4),('O-',45,4),('A+',50,5),('A-',984,5),('B+',434,5),('B-',5653,5),('AB+',246,5),('AB-',33,5),('O+',500,5),('O-',345,5),('A+',65,6),('A-',50,6),('B+',400,6),('B-',900,6),('AB+',555,6),('AB-',1000,6),('O+',350,6),('O-',760,6);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 21:33:58
