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
-- Table structure for table `afectiuni_medicale_primitori`
--

DROP TABLE IF EXISTS `afectiuni_medicale_primitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `afectiuni_medicale_primitori` (
  `primitorID` int NOT NULL,
  `afectiuneID` int NOT NULL,
  `data_diagnostic` date DEFAULT NULL,
  `tratament` varchar(200) DEFAULT NULL,
  KEY `FK_primitorID_afectiuniMedicalePrimitori` (`primitorID`),
  KEY `FK_afectiuneID_afectiuniMedicalePrimitori` (`afectiuneID`),
  CONSTRAINT `FK_afectiuneID_afectiuniMedicalePrimitori` FOREIGN KEY (`afectiuneID`) REFERENCES `afectiuni_medicale` (`afectiuneID`),
  CONSTRAINT `FK_primitorID_afectiuniMedicalePrimitori` FOREIGN KEY (`primitorID`) REFERENCES `primitor` (`primitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afectiuni_medicale_primitori`
--

LOCK TABLES `afectiuni_medicale_primitori` WRITE;
/*!40000 ALTER TABLE `afectiuni_medicale_primitori` DISABLE KEYS */;
INSERT INTO `afectiuni_medicale_primitori` VALUES (3,1,'2022-06-25','Transfuzii de sânge'),(4,2,'2021-11-12','Imatinib'),(5,3,'2023-02-18','Factor de coagulare'),(6,4,'2022-09-30','Transfuzii de trombocite'),(7,2,'2023-01-15','Anemie'),(8,1,'2023-12-19','fier'),(8,4,'2023-12-01','aaa');
/*!40000 ALTER TABLE `afectiuni_medicale_primitori` ENABLE KEYS */;
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
