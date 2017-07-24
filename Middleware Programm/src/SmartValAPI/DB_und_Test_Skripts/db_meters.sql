-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 23. Nov 2016 um 21:31
-- Server Version: 5.5.52-0+deb8u1
-- PHP-Version: 5.6.24-0+deb8u1
-- zur Verfügung gestellt von Christian Peuker

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `db_meters`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
`customer_id` int(11) NOT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `firstname` varchar(20) DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `alias` varchar(20) DEFAULT NULL,
  `company_customer_id` varchar(20) DEFAULT NULL,
  `signature` varchar(20) DEFAULT NULL,
  `key` varchar(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `customer`
--

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(1, 'Mustermann', 'Max', 'Hauptplatz 1', '5020', 'Salzburg', 'Fake-Me', 'S123456', 'Fake-Signature', 'Fake-Key');

-- --------------------------------------------------------

--
-- Stellvertreter-Struktur des Views `last_data`
--
CREATE TABLE IF NOT EXISTS `last_data` (
`data_id` int(11)
,`meter_id` int(11)
,`timestamp` timestamp
,`count_total` double
,`count_register1` double
,`count_register2` double
,`count_register3` double
,`count_register4` double
,`power_p1` double
,`power_p2` double
,`power_p3` double
,`work_p1` double
,`work_p2` double
,`work_p3` double
,`frequency` double
,`voltage` double
);
-- --------------------------------------------------------

--
-- Stellvertreter-Struktur des Views `last_events`
--
CREATE TABLE IF NOT EXISTS `last_events` (
`log_id` int(11)
,`id_type` int(11)
,`content` varchar(100)
,`source_target` varchar(20)
,`timestamp` timestamp
);
-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `log_events`
--

CREATE TABLE IF NOT EXISTS `log_events` (
`log_id` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  `content` varchar(100) NOT NULL,
  `source_target` varchar(20) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `log_events`
--

INSERT INTO `log_events` (`log_id`, `id_type`, `content`, `source_target`, `timestamp`) VALUES
(1, 2, 'New Database created', 'Admin', '0000-00-00 00:00:00'),
(2, 2, 'Starting application!', 'SmartReader', '2016-11-04 14:36:41'),
(3, 2, 'Start collecting from Meter!', 'GO00005358', '2016-11-04 14:36:46'),
(4, 1, 'Quitting application!', 'SmartReader', '2016-11-04 14:37:54'),
(5, 2, 'Starting application!', 'SmartReader', '2016-11-04 14:41:04'),
(6, 2, 'Start collecting from Meter!', 'GO00005358', '2016-11-04 14:41:10'),
(7, 1, 'Quitting application!', 'SmartReader', '2016-11-05 18:03:31'),
(8, 2, 'Starting application!', 'SmartReader', '2016-11-05 18:03:44'),
(9, 1, 'Error while trying to connect to meter!', 'GO00005358', '2016-11-05 18:03:46'),
(10, 1, 'Quitting application!', 'SmartReader', '2016-11-05 18:03:46'),
(11, 2, 'Starting application!', 'SmartReader', '2016-11-05 18:05:13'),
(12, 2, 'Start collecting from Meter!', 'GO00005358', '2016-11-05 18:05:19'),
(13, 2, 'Starting application!', 'SmartReader', '2016-11-23 20:18:02'),
(14, 1, 'Error while trying to connect to meter!', 'GO00005358', '2016-11-23 20:18:04'),
(15, 1, 'Quitting application!', 'SmartReader', '2016-11-23 20:18:05');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `log_types`
--

CREATE TABLE IF NOT EXISTS `log_types` (
`types_id` int(11) NOT NULL,
  `types_description` varchar(15) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `log_types`
--

INSERT INTO `log_types` (`types_id`, `types_description`) VALUES
(1, 'Admin-Error'),
(2, 'Admin-Info'),
(3, 'Customer-Info');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `meter_data`
--

CREATE TABLE IF NOT EXISTS `meter_data` (
`data_id` int(11) NOT NULL,
  `meter_id` int(11) NOT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `count_total` double DEFAULT NULL,
  `count_register1` double DEFAULT NULL,
  `count_register2` double DEFAULT NULL,
  `count_register3` double DEFAULT NULL,
  `count_register4` double DEFAULT NULL,
  `power_p1` double DEFAULT NULL,
  `power_p2` double DEFAULT NULL,
  `power_p3` double DEFAULT NULL,
  `work_p1` double DEFAULT NULL,
  `work_p2` double DEFAULT NULL,
  `work_p3` double DEFAULT NULL,
  `frequency` double DEFAULT NULL,
  `voltage` double DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `meter_data`
--

INSERT INTO `meter_data` (`data_id`, `meter_id`, `timestamp`, `count_total`, `count_register1`, `count_register2`, `count_register3`, `count_register4`, `power_p1`, `power_p2`, `power_p3`, `work_p1`, `work_p2`, `work_p3`, `frequency`, `voltage`) VALUES
(1, 2, '2016-11-05 18:15:13', 241.52, 116.97, 124.56, 0, 0, 0, -1, -1, -1, -1, -1, 50.02, 237.59);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `meter_management`
--

CREATE TABLE IF NOT EXISTS `meter_management` (
`id_meter` int(11) NOT NULL,
  `description` varchar(15) DEFAULT NULL,
  `serial` varchar(15) DEFAULT NULL,
  `key` varchar(30) DEFAULT NULL,
  `id_type` int(11) NOT NULL,
  `id_manufactor` int(11) NOT NULL,
  `id_protocol` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `period` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '0',
  `port` varchar(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `meter_management`
--

INSERT INTO `meter_management` (`id_meter`, `description`, `serial`, `key`, `id_type`, `id_manufactor`, `id_protocol`, `id_customer`, `period`, `active`, `port`) VALUES
(1, 'MT830-3080', '35770817', NULL, 1, 1, 1, 1, 15, 0, '/dev/ttyUSB0'),
(2, 'Echelon', 'GO00005358', '774e67fae36c70a99d8c', 1, 3, 2, 1, 30, 1, '/dev/ttyUSB0');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `meter_manufactor`
--

CREATE TABLE IF NOT EXISTS `meter_manufactor` (
`manufactor_id` int(11) NOT NULL,
  `description` varchar(15) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `meter_manufactor`
--

INSERT INTO `meter_manufactor` (`manufactor_id`, `description`) VALUES
(1, 'Iskraemeco'),
(2, 'Itron'),
(3, 'Echelon');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `meter_protocol`
--

CREATE TABLE IF NOT EXISTS `meter_protocol` (
`protocol_id` int(11) NOT NULL,
  `protocol` varchar(30) DEFAULT NULL,
  `data_scheme` varchar(30) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `meter_protocol`
--

INSERT INTO `meter_protocol` (`protocol_id`, `protocol`, `data_scheme`) VALUES
(1, 'DIN EN 62056-21', 'DIN EN 62056-61 V2'),
(2, 'DIN EN 62056-21', 'DIN EN 62056-61 V1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `meter_type`
--

CREATE TABLE IF NOT EXISTS `meter_type` (
`type_id` int(11) NOT NULL,
  `description` varchar(15) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `meter_type`
--

INSERT INTO `meter_type` (`type_id`, `description`) VALUES
(1, 'Electric'),
(2, 'Water'),
(3, 'Heating');

-- --------------------------------------------------------

--
-- Struktur des Views `last_data`
--
DROP TABLE IF EXISTS `last_data`;

CREATE ALGORITHM=UNDEFINED DEFINER=`test`@`%` SQL SECURITY DEFINER VIEW `last_data` AS select `meter_data`.`data_id` AS `data_id`,`meter_data`.`meter_id` AS `meter_id`,`meter_data`.`timestamp` AS `timestamp`,`meter_data`.`count_total` AS `count_total`,`meter_data`.`count_register1` AS `count_register1`,`meter_data`.`count_register2` AS `count_register2`,`meter_data`.`count_register3` AS `count_register3`,`meter_data`.`count_register4` AS `count_register4`,`meter_data`.`power_p1` AS `power_p1`,`meter_data`.`power_p2` AS `power_p2`,`meter_data`.`power_p3` AS `power_p3`,`meter_data`.`work_p1` AS `work_p1`,`meter_data`.`work_p2` AS `work_p2`,`meter_data`.`work_p3` AS `work_p3`,`meter_data`.`frequency` AS `frequency`,`meter_data`.`voltage` AS `voltage` from `meter_data` where 1 order by `meter_data`.`meter_id` desc limit 10;

-- --------------------------------------------------------

--
-- Struktur des Views `last_events`
--
DROP TABLE IF EXISTS `last_events`;

CREATE ALGORITHM=UNDEFINED DEFINER=`test`@`%` SQL SECURITY DEFINER VIEW `last_events` AS select `log_events`.`log_id` AS `log_id`,`log_events`.`id_type` AS `id_type`,`log_events`.`content` AS `content`,`log_events`.`source_target` AS `source_target`,`log_events`.`timestamp` AS `timestamp` from `log_events` where 1 order by `log_events`.`log_id` desc limit 10;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `customer`
--
ALTER TABLE `customer`
 ADD PRIMARY KEY (`customer_id`);

--
-- Indizes für die Tabelle `log_events`
--
ALTER TABLE `log_events`
 ADD PRIMARY KEY (`log_id`,`id_type`), ADD KEY `FK_types` (`id_type`);

--
-- Indizes für die Tabelle `log_types`
--
ALTER TABLE `log_types`
 ADD PRIMARY KEY (`types_id`);

--
-- Indizes für die Tabelle `meter_data`
--
ALTER TABLE `meter_data`
 ADD PRIMARY KEY (`data_id`,`meter_id`), ADD KEY `fk_meter_data_meter_management1_idx` (`meter_id`);

--
-- Indizes für die Tabelle `meter_management`
--
ALTER TABLE `meter_management`
 ADD PRIMARY KEY (`id_meter`,`id_type`,`id_manufactor`,`id_protocol`,`id_customer`), ADD KEY `fk_meter_management_meter_type1_idx` (`id_type`), ADD KEY `fk_meter_management_meter_protocol1_idx` (`id_protocol`), ADD KEY `fk_meter_management_meter_manufactor1_idx` (`id_manufactor`), ADD KEY `fk_meter_management_customer1_idx` (`id_customer`);

--
-- Indizes für die Tabelle `meter_manufactor`
--
ALTER TABLE `meter_manufactor`
 ADD PRIMARY KEY (`manufactor_id`);

--
-- Indizes für die Tabelle `meter_protocol`
--
ALTER TABLE `meter_protocol`
 ADD PRIMARY KEY (`protocol_id`);

--
-- Indizes für die Tabelle `meter_type`
--
ALTER TABLE `meter_type`
 ADD PRIMARY KEY (`type_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `customer`
--
ALTER TABLE `customer`
MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `log_events`
--
ALTER TABLE `log_events`
MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT für Tabelle `log_types`
--
ALTER TABLE `log_types`
MODIFY `types_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `meter_data`
--
ALTER TABLE `meter_data`
MODIFY `data_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `meter_management`
--
ALTER TABLE `meter_management`
MODIFY `id_meter` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `meter_manufactor`
--
ALTER TABLE `meter_manufactor`
MODIFY `manufactor_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `meter_protocol`
--
ALTER TABLE `meter_protocol`
MODIFY `protocol_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `meter_type`
--
ALTER TABLE `meter_type`
MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `log_events`
--
ALTER TABLE `log_events`
ADD CONSTRAINT `fk_LOG_EVENTS_LOG_TYPES` FOREIGN KEY (`id_type`) REFERENCES `log_types` (`types_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `meter_data`
--
ALTER TABLE `meter_data`
ADD CONSTRAINT `fk_meter_data_meter_management1` FOREIGN KEY (`meter_id`) REFERENCES `meter_management` (`id_meter`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `meter_management`
--
ALTER TABLE `meter_management`
ADD CONSTRAINT `fk_meter_management_customer1` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_meter_management_meter_manufactor1` FOREIGN KEY (`id_manufactor`) REFERENCES `meter_manufactor` (`manufactor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_meter_management_meter_protocol1` FOREIGN KEY (`id_protocol`) REFERENCES `meter_protocol` (`protocol_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_meter_management_meter_type1` FOREIGN KEY (`id_type`) REFERENCES `meter_type` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
