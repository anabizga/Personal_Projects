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
-- Temporary view structure for view `vedereafectiunimedicaledonatori`
--

DROP TABLE IF EXISTS `vedereafectiunimedicaledonatori`;
/*!50001 DROP VIEW IF EXISTS `vedereafectiunimedicaledonatori`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vedereafectiunimedicaledonatori` AS SELECT 
 1 AS `NumeDonator`,
 1 AS `PrenumeDonator`,
 1 AS `DataDiagnostic`,
 1 AS `Tratament`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vederedonatoridonatii`
--

DROP TABLE IF EXISTS `vederedonatoridonatii`;
/*!50001 DROP VIEW IF EXISTS `vederedonatoridonatii`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vederedonatoridonatii` AS SELECT 
 1 AS `NumeDonator`,
 1 AS `PrenumeDonator`,
 1 AS `GrupaSangeDonator`,
 1 AS `DataDonatie`,
 1 AS `CantitateDonata`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vederestocsange`
--

DROP TABLE IF EXISTS `vederestocsange`;
/*!50001 DROP VIEW IF EXISTS `vederestocsange`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vederestocsange` AS SELECT 
 1 AS `GrupaSange`,
 1 AS `CantitateDisponibila`,
 1 AS `NumeCentru`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vedereafectiunimedicaledonatori`
--

/*!50001 DROP VIEW IF EXISTS `vedereafectiunimedicaledonatori`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vedereafectiunimedicaledonatori` AS select `id`.`nume` AS `NumeDonator`,`id`.`prenume` AS `PrenumeDonator`,`am`.`data_diagnostic` AS `DataDiagnostic`,`am`.`tratament` AS `Tratament` from (`info_donator` `id` join `afectiuni_medicale_donatori` `am` on((`id`.`donatorID` = `am`.`donatorID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vederedonatoridonatii`
--

/*!50001 DROP VIEW IF EXISTS `vederedonatoridonatii`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vederedonatoridonatii` AS select `id`.`nume` AS `NumeDonator`,`id`.`prenume` AS `PrenumeDonator`,`id`.`grupa_sange` AS `GrupaSangeDonator`,`don`.`data_donatie` AS `DataDonatie`,`don`.`cantitate` AS `CantitateDonata` from (`info_donator` `id` join `donatii` `don` on((`id`.`donatorID` = `don`.`donatorID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vederestocsange`
--

/*!50001 DROP VIEW IF EXISTS `vederestocsange`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vederestocsange` AS select `s`.`grupa_sange` AS `GrupaSange`,`s`.`cantitate` AS `CantitateDisponibila`,`c`.`nume_centru` AS `NumeCentru` from (`stock` `s` join `centru` `c` on((`s`.`centruID` = `c`.`centruID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping events for database 'centrudonaresange'
--

--
-- Dumping routines for database 'centrudonaresange'
--
/*!50003 DROP PROCEDURE IF EXISTS `AfisareCentreSange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AfisareCentreSange`(
    IN grupa_sange_param VARCHAR(3),
    IN cantitate_disponibila_param INT
)
BEGIN
    SELECT c.centruID, c.nume_centru
    FROM centru c
    JOIN stock s ON c.centruID = s.centruID
    WHERE s.grupa_sange = grupa_sange_param AND s.cantitate >= cantitate_disponibila_param;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrareAfectiuneDonator` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrareAfectiuneDonator`(
    IN donatorID_param INT,
    IN afectiuneID_param INT,
    IN data_diagnostic_param DATE,
    IN tratament_param VARCHAR(200),
    IN cantitate_maxima_param INT
)
BEGIN
    -- Verificăm dacă boala este anemie și ajustăm cantitatea maximă corespunzător
    IF afectiuneID_param = 1 THEN
        SET cantitate_maxima_param = 200; 
	ELSEIF afectiuneID_param = 2 THEN
        SET cantitate_maxima_param = 300; 
	ELSEIF afectiuneID_param = 3 THEN
        SET cantitate_maxima_param = 400; 
	ELSEIF afectiuneID_param = 4 THEN
        SET cantitate_maxima_param = 500; 
    END IF;

    INSERT INTO afectiuni_Medicale_Donatori (donatorID, afectiuneID, data_diagnostic, tratament, cantitate_maxima)
    VALUES (donatorID_param, afectiuneID_param, data_diagnostic_param, tratament_param, cantitate_maxima_param);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrareAfectiunePrimitor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrareAfectiunePrimitor`(IN primitorID_param INT,IN afectiuneID_param INT,IN data_diagnostic_param DATE,IN tratament_param VARCHAR(200))
BEGIN
    INSERT INTO afectiuni_Medicale_Primitori (primitorID, afectiuneID, data_diagnostic, tratament)
    VALUES (primitorID_param, afectiuneID_param, data_diagnostic_param, tratament_param);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrareCerere` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrareCerere`(
	IN primitorID_param INT,
    IN data_cerere_param DATE,
    IN cantitate_sange_param INT,
    IN centruID_param INT
)
BEGIN
        DECLARE cerereID_param INT; 

        INSERT INTO cereri (primitorID, data_cerere, cantitate, centruID)
        VALUES (primitorID_param, data_cerere_param, cantitate_sange_param, centruID_param);
        
        SET cerereID_param = LAST_INSERT_ID();
        SELECT 'Înregistrare cerere reușită' AS rezultat, cerereID_param AS cerereID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrareDonatie` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrareDonatie`(
    IN donatorID_param INT,
    IN data_donatie_param DATE,
    IN cantitate_sange_param INT,
    IN centruID_param INT
)
BEGIN
    DECLARE max_cantitate_permis INT;
    DECLARE donatieID_param INT;  

    SELECT min(cantitate_maxima)
    INTO max_cantitate_permis
    FROM afectiuni_Medicale_Donatori
    WHERE donatorID = donatorID_param;

    IF max_cantitate_permis IS NULL THEN
        SET max_cantitate_permis = 500;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM donator WHERE donatorID = donatorID_param) THEN
        SELECT 'Donatorul nu există. Înregistrați donatorul înainte de a adăuga donații.' AS rezultat, NULL AS donatieID;
    ELSE
        IF cantitate_sange_param > max_cantitate_permis THEN
            SELECT 'Cantitatea depășește limita permisă pentru donatorul cu afecțiune medicală.' AS rezultat, NULL AS donatieID;
        ELSE
            INSERT INTO donatii (donatorID, data_donatie, cantitate, centruID)
            VALUES (donatorID_param, data_donatie_param, cantitate_sange_param, centruID_param);
            SET donatieID_param = LAST_INSERT_ID();
            SELECT 'Înregistrare donație reușită' AS rezultat, donatieID_param AS donatieID;
        END IF;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrareDonator` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrareDonator`(
    IN newUsername VARCHAR(20),
    IN newPassword VARCHAR(30),
    IN newEmail VARCHAR(40),
    IN nume_param VARCHAR(20),
    IN prenume_param VARCHAR(20),
    IN cnp_param VARCHAR(25),
    IN gen_param VARCHAR(2),
    IN grupa_sange_param VARCHAR(3)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM donator WHERE username = newUsername) THEN
        INSERT INTO donator (username, password, email) VALUES (newUsername, newPassword, newEmail);
        SET @donatorID_param = LAST_INSERT_ID();
        INSERT INTO info_donator (donatorID, nume, prenume, cnp, gen, grupa_sange) 
        VALUES (@donatorID_param, nume_param, prenume_param, cnp_param, gen_param, grupa_sange_param);
        SELECT 'Inregistrare reusita' AS rezultat;
    ELSE
        SELECT 'Numele de utilizator exista deja. Alegeți altul.' AS rezultat;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InregistrarePrimitor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InregistrarePrimitor`(
    IN newUsername VARCHAR(20),
    IN newPassword VARCHAR(30),
    IN newEmail VARCHAR(40),
    IN nume_param VARCHAR(20),
    IN prenume_param VARCHAR(20),
    IN cnp_param VARCHAR(25),
    IN gen_param VARCHAR(2),
    IN grupa_sange_param VARCHAR(3)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM primitor WHERE username = newUsername) THEN
        INSERT INTO primitor (username, password, email) VALUES (newUsername, newPassword, newEmail);
        SET @primitorID_param = LAST_INSERT_ID();
        INSERT INTO info_primitor (primitorID, nume, prenume, cnp, gen, grupa_sange) 
        VALUES (@primitorID_param, nume_param, prenume_param, cnp_param, gen_param, grupa_sange_param);
        SELECT 'Inregistrare reusita' AS rezultat;
    ELSE
        SELECT 'Numele de utilizator exista deja. Alegeți altul.' AS rezultat;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RaportStocSange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `RaportStocSange`(IN centruID_param INT)
BEGIN
    SELECT grupa_sange, cantitate
    FROM stock
    WHERE centruID = centruID_param;
END ;;
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

-- Dump completed on 2024-03-29 21:33:58
