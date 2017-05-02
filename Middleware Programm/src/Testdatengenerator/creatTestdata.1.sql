########################################################################
# Description: leert bestehende Tabellen aus und legt danach 
#              konsistente Stammdaten an.
#
# Die Tabellen müssen bereits bestehen, also mit EER..... angelegt 
# worden sein.
#
########################################################################

# ----- Tabellen entleeren ----- 
delete from meter_data;
delete from meter_management;
delete from meter_protocol;
delete from meter_manufactor;
delete from customer;

# ----- Stammtabellen befüllen ------ 
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(1, 'Mustermann', 'Max', 'Hauptplatz 1', '5020', 'Salzburg', '', 'S123456', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(2, 'REDD', 'Haus 1', 'Street 1', '12345', 'Cambridge', '', 'REDD01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(3, 'REDD', 'Haus 2', 'Street 1', '12345', 'Cambridge', '', 'REDD02', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(4, 'REDD', 'Haus 3', 'Street 1', '12345', 'Cambridge', '', 'REDD03', '', '');

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(5, 'AG Kunde 1', 'Salzburg', 'Dorfplatz 1', '5203', 'Köstendorf', '', 'SAG01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(6, 'AG Kunde 2', 'Salzburg', 'Dorfplatz 4', '5203', 'Köstendorf', '', 'SAG02', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(7, 'AG Kunde 3', 'Salzburg', 'Dorfplatz 6', '5203', 'Köstendorf', '', 'SAG03', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(8, 'AG Kunde 4', 'Salzburg', 'Dorfplatz 6', '5203', 'Köstendorf', '', 'SAG04', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(9, 'AG Kunde 5', 'Salzburg', 'Dorfplatz 6', '5203', 'Köstendorf', '', 'SAG05', '', '');

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(10, 'AG Kunde 1', 'Energie AG', 'Zentralplatz 25', '4020', 'Linz', '', 'EAG01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(11, 'AG Kunde 2', 'Energie AG', 'Zentralplatz 31', '4020', 'Linz', '', 'EAG02', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(12, 'AG Kunde 2', 'Energie AG', 'Zentralplatz 56', '4020', 'Linz', '', 'EAG03', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(13, 'AG Kunde 3', 'Energie AG', 'Zentralplatz 12', '4020', 'Linz', '', 'EAG04', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(14, 'AG Kunde 4', 'Energie AG', 'Zentralplatz 134', '4020', 'Linz', '', 'EAG05', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(15, 'AG Kunde 5', 'Energie AG', 'Zentralplatz 98', '4020', 'Linz', '', 'EAG06', '', '');

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(16, 'DALE customer 1', 'UK', 'Castle Way 25', 'SA73', 'Dale', '', 'DAL01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(17, 'DALE customer 2', 'UK', 'Point Farm CAmp Side', 'SA73', 'Dale', '', 'DAL02', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(18, 'DALE customer 3', 'UK', 'Dale Yacht Club 2', 'SA73', 'Dale', '', 'DAL03', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(19, 'DALE customer 4', 'UK', 'Brunt, Haverfordwest', 'SA73', 'Dale', '', 'DAL04', '', '');

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(20, 'ADRES customer 1', 'AT', 'Triesterstrasse 123', '1100', 'Wien', '', 'ADR01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(21, 'ADRES customer 2', 'AT', 'Stephansplatz 5', '1010', 'Wien', '', 'ADR06', '', '');

INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(22, 'Customer 1', 'GREEND', 'Brennerstrasse 5', '6156', 'Gries am Brenner', '', 'GRN01', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(23, 'Customer 2', 'GREEND', 'Brennerstrasse 27', '6156', 'Gries am Brenner', '', 'GRN09', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(24, 'Customer 3', 'GREEND', 'Dorfplatz 26', '6156', 'Gries am Brenner', '', 'GRN12', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(25, 'Customer 1', 'GREEND', 'Strada dello Brennero 11', '39041', 'Brennero', '', 'GRN19', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(26, 'Customer 1', 'GREEND', 'Pflersch 23', '39041', 'Brennero', '', 'GRN29', '', '');
INSERT INTO `customer` (`customer_id`, `lastname`, `firstname`, `street`, `postal_code`, `city`, `alias`, `company_customer_id`, `signature`, `key`) VALUES
(27, 'Customer 1', 'GREEND', 'Via Tribulaun 93', '39041', 'Brennero', '', 'GRN89', '', '');

-- Protokolle
INSERT INTO `meter_protocol` VALUES 
(1,'DIN EN 62056-21','DIN EN 62056-61 V2'),
(2,'DIN EN 62056-21','DIN EN 62056-61 V1'),
(3,'OEN EN 1234','OEN EN 1234'),
(4,'OEN EN 0815','OEN EN 0815'),
(5,'OEN EN 4711','OEN EN 4711');

-- Meter Hersteller 
INSERT INTO `meter_manufactor` (`manufactor_id`, `description`) VALUES
(1, 'Iskraemeco'),
(2, 'Itron'),
(3, 'Echelon'),
(4, 'Kamstrup'),
(5, 'EDMI'),
(6, 'Kaifa');

-- Meter Geräte
INSERT INTO `meter_management` (`id_meter`, `description`, `serial`, `key`, `id_type`, `id_manufactor`, `id_protocol`, `id_customer`, `period`, `active`, `port`) VALUES
(1, 'MT830-3080', '35770817',   NULL, 1, 1, 1, 1, 15, 0, '/dev/ttyUSB0'),
(2, 'Echelon', 'GO00005358',    NULL, 1, 3, 1, 1, 30, 1, '/dev/ttyUSB0'),
(3, 'Kamstrup Mul402', '1234',  NULL, 1, 4, 1, 4, 15, 0, '/dev/ttyUSB0'),
(4, 'Kaifa MA304', '5678',      NULL, 1, 6, 1, 4, 15, 0, '/dev/ttyUSB0'),
(5, 'Kaifa DTSF67', '91011',    NULL, 1, 6, 1, 4, 15, 0, '/dev/ttyUSB0'),
(6, 'Kaifa MA310', '12131',     NULL, 1, 6, 1, 4, 15, 0, '/dev/ttyUSB0'),
(7, 'EDMI Mk10A', '4151617',    NULL, 1, 5, 1, 4, 15, 0, '/dev/ttyUSB0'),
(8, 'EDMI Mk10B', '18192',      NULL, 1, 5, 1, 5, 15, 0, '/dev/ttyUSB0'),
(9, 'EDMI Mk10D', '02122',      NULL, 1, 5, 1, 5, 15, 0, '/dev/ttyUSB0'),
(10, 'Polyphase R300', 'ITR23', NULL, 1, 2, 1, 1, 15, 1, '/dev/ttyUSB0'),
(11, 'Kaifa MA310', '19720122', NULL, 1, 5, 1, 22, 15, 0, '/dev/ttyUSB0'),
(12, 'Polyphase R300', '12453', NULL, 1, 5, 1, 23, 15, 0, '/dev/ttyUSB0'),
(13, 'Kamstrup Mul402', '1482', NULL, 1, 5, 1, 24, 15, 0, '/dev/ttyUSB0'),
(14, 'Echelon', '197501025',    NULL, 1, 5, 1, 25, 15, 0, '/dev/ttyUSB0'),
(15, 'Echelon', '197501026',    NULL, 1, 5, 1, 26, 15, 0, '/dev/ttyUSB0'),
(16, 'Echelon', '197501026',    NULL, 1, 5, 1, 27, 15, 0, '/dev/ttyUSB0'),
(17, 'EDMI Mk10D', '02123',     NULL, 1, 5, 1, 6, 15, 0, '/dev/ttyUSB0'),
(18, 'EDMI Mk10D', '02124',     NULL, 1, 5, 1, 7, 15, 0, '/dev/ttyUSB0'),
(19, 'EDMI Mk10D', '02125',     NULL, 1, 5, 1, 8, 15, 0, '/dev/ttyUSB0'),
(20, 'EDMI Mk10D', '02126',     NULL, 1, 5, 1, 9, 15, 0, '/dev/ttyUSB0'),
(21, 'Kamstrup Mu4020', '070304-150-1',  NULL, 1, 4, 1, 10, 15, 0, '/dev/ttyUSB0'),
(22, 'Kamstrup Mu4020', '070304-150-2',  NULL, 1, 4, 1, 11, 15, 0, '/dev/ttyUSB0'),
(23, 'Kamstrup Mu4020', '070304-150-3',  NULL, 1, 4, 1, 12, 15, 0, '/dev/ttyUSB0'),
(24, 'Kamstrup Mu4020', '070304-150-4',  NULL, 1, 4, 1, 13, 15, 0, '/dev/ttyUSB0'),
(25, 'Kamstrup Mu4020', '070304-150-5',  NULL, 1, 4, 1, 14, 15, 0, '/dev/ttyUSB0'),
(26, 'Kamstrup Mu4020', '070304-150-6',  NULL, 1, 4, 1, 15, 15, 0, '/dev/ttyUSB0'),
(27, 'Echelon', '2016-01a',    NULL, 1, 5, 1, 2, 15, 0, '/dev/ttyUSB0'),
(28, 'EDMI Mk10D', '2017-02a',     NULL, 1, 5, 1, 3, 15, 0, '/dev/ttyUSB0');



-- meter_data itself -> do not create testdata here
-- INSERT INTO `meter_data` (`data_id`, `meter_id`, `timestamp`, `count_total`, `count_register1`, `count_register2`, `count_register3`, `count_register4`, `power_p1`, `power_p2`, `power_p3`, `work_p1`, `work_p2`, `work_p3`, `frequency`, `voltage`) VALUES
-- (1, 2, '2016-11-05 18:15:13', 241.52, 116.97, 124.56, 0, 0, 0, -1, -1, -1, -1, -1, 50.02, 237.59);
