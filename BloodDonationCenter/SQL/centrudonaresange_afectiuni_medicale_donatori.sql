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
-- Table structure for table `afectiuni_medicale_donatori`
--

DROP TABLE IF EXISTS `afectiuni_medicale_donatori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `afectiuni_medicale_donatori` (
  `donatorID` int NOT NULL,
  `afectiuneID` int NOT NULL,
  `data_diagnostic` date DEFAULT NULL,
  `tratament` varchar(200) DEFAULT NULL,
  `cantitate_maxima` int NOT NULL,
  KEY `FK_donatorID_afectiuniMedicaleDonatori` (`donatorID`),
  KEY `FK_afectiuneID_afectiuniMedicaleDonatori` (`afectiuneID`),
  CONSTRAINT `FK_afectiuneID_afectiuniMedicaleDonatori` FOREIGN KEY (`afectiuneID`) REFERENCES `afectiuni_medicale` (`afectiuneID`),
  CONSTRAINT `FK_donatorID_afectiuniMedicaleDonatori` FOREIGN KEY (`donatorID`) REFERENCES `donator` (`donatorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afectiuni_medicale_donatori`
--

LOCK TABLES `afectiuni_medicale_donatori` WRITE;
/*!40000 ALTER TABLE `afectiuni_medicale_donatori` DISABLE KEYS */;
INSERT INTO `afectiuni_medicale_donatori` VALUES (1,1,'2022-05-15','Suplimente de fier',400),(2,2,'2021-10-20','Chemoterapie',200),(3,3,'2023-01-10','Factor de coagulare',500),(4,4,'2022-08-05','Transfuzii de trombocite',600),(1,1,'2022-05-15','Suplimente de fier',270),(1,4,'2022-05-15','Suplimente de fier',270),(1,3,'2022-05-15','Suplimente de fier',270),(1,1,'2022-05-15','Suplimente de fier',200),(1,1,'2022-05-15','Suplimente de fier',200),(1,4,'2022-05-15','Suplimente de fier',270),(1,3,'2022-05-15','Suplimente de fier',270),(1,1,'2022-05-15','Suplimente de fier',200),(1,4,'2022-05-15','Suplimente de fier',500),(1,3,'2022-05-15','Suplimente de fier',400),(1,3,'2023-12-19','pastile',400),(9,1,'2023-12-04','ldasgdfgdf',200),(1,3,'2023-12-13','aaa',400),(1,4,'2023-12-29','a',500);
/*!40000 ALTER TABLE `afectiuni_medicale_donatori` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 21:33:56
