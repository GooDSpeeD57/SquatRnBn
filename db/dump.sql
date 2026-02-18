
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: squatrbnb
-- ------------------------------------------------------
-- Server version	8.4.6

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
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'HOTE'),(3,'MODERATION'),(1,'UTILISATEUR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_naissance` date NOT NULL,
  `photo_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_user_role` (`role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'test10','rourou','rara','rourou@rara.fr','1900-01-12','uploads/users/39a65b39-f8ee-4425-8b09-b3275b727dcb.jpg','$2a$10$yIWN1bvwwwzXdI3JbXrgnebiGvtKguFGHpqt7yX9RK9x87gSg2rNy',NULL,1),(5,'neojero','boebion','jerome','boebion@gmail.com','1976-12-22','uploads/users/7941fd94-7a2b-4d4a-95c1-5859327e2c38.jpg','$2a$12$ONKHP43onRXiW9irxtUWNOQO8naUWMgUOK57N6CMKE4zSlUD3wyA2',NULL,1),(6,'reraeaze','azeazea','azeae','azeazeae@aaze.rt','0001-01-01',NULL,'$2a$12$8pIhAayh5oMhfEtXAYC7RulR0PACOzGbAOG5hEC0itIUDU6nuUiUa',NULL,2),(7,'azeaze','azeaze','azeae','a@a.a','2000-01-01','uploads/users/a1ffdbc1-b13d-4dd1-9f2b-add0b4b43041.jpg','$2a$12$2iQioMkYcet8X5A7ffuvou9xxTWdwZG2R7F9gNHpg0yxF3D7NnCeq',NULL,2),(9,'teste','test','test','test@test.test','2001-01-01','uploads/users/56e9d76c-c2ee-48c1-82ca-43142827e65e.jpg','$2a$12$oaoFA/LuN0JPx5DGTFRgcu24VL4gNc6wgHkXCu.w0KWJqGnN9v.FS',NULL,1),(10,'tast','tast','tast','tast@tast.tast','2026-12-05','uploads/users/353a003c-6540-4742-83f0-3d4ea3d284ba.jpg','$2a$12$MAAaqXrB0GGnVAXwoXr3z.77565KbtE.T/O4gmSy/R2wed3izMDTC',NULL,1),(11,'tust','tust','tust','tust@tust.tust','1999-05-14',NULL,'$2a$12$TmF8q653x5zVAc8Mit40iOUIe1DjGOPfIyi9nwAPut1ohJcygMiHC',NULL,1),(12,'SuperAdmin','Kent','Clarc','Clarckent@super.man','2000-01-01','uploads/users/f2f30690-bf43-4925-90b1-0e43528cf782.gif','$2a$12$x111NQsdcp6Hpn1HtjrAreP.eRhVnamAu7RxtTfTM6CqSvL0AD7b2','63e8f13a-b828-478d-a2a8-a7b3abedcc5a',3),(13,'Batman','Wane','Bruce','Batman@wane.bruce','1964-05-14','uploads/users/0e24025e-b109-4da6-a7b5-d0399941a9a4.JPG','$2a$12$paYfoQVSM0CFMNKtPWOthupUOU2zahR1D/zhUatyzGAzSKaMjbeQq',NULL,1),(15,'Batmanleretour','wayne','Bruce','Batman2@wane.bruce','2000-01-01','uploads/users/b87aaf5b-260d-4d5c-953b-d3e10ff4121b.JPG','$2a$12$cX35nkVvh7xkk.ufmgSKK.J9zctos7W/7mbeVz.L2G27/yCTTa.Bi',NULL,1),(16,'Batman3','Wayne','Bruce','Batman3@wayne.bruce','2000-01-01','uploads/users/f3470486-c2a7-49c1-9fed-b9428a1de53a.JPG','$2a$12$CGWOUsa7jjpNv.cWquhSJOtUVznqpOvLvjxyZK5XrV7LUDnPYDN6C',NULL,1),(20,'GooDSpeeD1','taesch','Julien','juju.taesch@gmail.com','1982-05-14',NULL,'$2a$10$kl9P764Qbqo/XjhUs3ePseq94bmYQJc1ZCUFLb4IypwA10xL.JIDC',NULL,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-05 10:15:25
