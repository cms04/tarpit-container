CREATE DATABASE `tarpit` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

-- tarpit.land definition

CREATE TABLE `tarpit.land` (
  `iso_code` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `land_name` varchar(255) NOT NULL,
  PRIMARY KEY (`iso_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- tarpit.server definition

CREATE TABLE `tarpit.server` (
  `ip` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `land` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`),
  KEY `FKqgxb87ewhj5ql5df9s6ddbqx0` (`land`),
  CONSTRAINT `FKqgxb87ewhj5ql5df9s6ddbqx0` FOREIGN KEY (`land`) REFERENCES `land` (`iso_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- tarpit.verbindungs_eintrag definition

CREATE TABLE `tarpit.verbindungs_eintrag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `eintrag_typ` varchar(255) NOT NULL,
  `port` int(11) NOT NULL,
  `zeitstempel` datetime(6) NOT NULL,
  `ip` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8iqydr4d3ownrx1q6h88rxhcp` (`ip`),
  CONSTRAINT `FK8iqydr4d3ownrx1q6h88rxhcp` FOREIGN KEY (`ip`) REFERENCES `server` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=3757 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;