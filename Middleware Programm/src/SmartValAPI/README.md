![N|Solid](https://www.fh-salzburg.ac.at/fileadmin/templates/img/FH_Salzburg_Logo_140.png)

# SmartValAPI - Installationsanleitung 
In dieser Installationsanleitung werden die Systemvoraussetzungen aufgezählt und der Vorgang der Installation beschrieben. Für die Standardinstallation ist keine der Änderungen im Abschnitt Konfiguration notwendig. 
Abschliessend werden noch zwei Standartdzugriffe dargestellt. Weitere Details finden sich im Abschlussbericht.

## Systemvoraussetzungen
Folgende Systemvoraussetzungen sind für den Einsatz von SmartValAPI erfüllen. Da SmartValAPI keine besonderen Anforderungen an das System stellt, ist eine Installation auf weiteren Platformen möglich, aber nicht gewährleistet.
* Betriebssystem: Windows Server 2012 R2, alternativ Windows 10 Home, Apple Mac OS X
* Java Umgebung: Java(TM) SE Runtime Environment (build 1.8.0_141-b15)
* Netzwerkverbindung (TCP/IP), sofern nicht nur lokal ausgewertet oder verteilt installiert wird 
* OpenLDAP 2.4.45, sofern dieser selbst betrieben wird
* Relationales Datenbanksystem: MySQL Server 5.7 
* NoSQL Datenbanksystem: MongoDB 3.4.7

Die folgenden Komponenten sind optional:

* Zur Durchführung der Tests: SOAPUI 5.3.0 
* Für eine Erweiterung oder das Neuübersetzen der Applikation: IntelliJ IDEA ULTIMATE 2017.2 
* Zur komfortablen Anpassung von Tabellen, Indizes oder Datenbankschemata: MySQL Workbench 6.3

## Installation 
Im Auslieferungsumfang von SmartValAPI befindet sich die übersetzte Software in Form eines Web Archives (**Pfad ergänzen**). Dieses wird mit 
```
java -jar SmartValAPI-1.0.0.war
```
gestartet. Vor dem Start ist zu gewährleisten, dass alle notwendigen Dienste (LDAP, MySQL, MongoDB) gestartet wurden und verfügbar sind. 

## Konfiguration

### LDAP 
**Strukturen??** Benutzerberechtigung, sonst was??

### MySQL 
SmartValAPI setzt ein Datebankschema mit dem Namen *smart_meter* voraus, mit einem Benutzer, der die Berechtigungen auf diese Datenbank hat, werden das Schema, die Tabellen und Indizes mit den Skripts *DB_und_Test_Skripts/db_meters.sql* und  *DB_und_Test_Skripts/createSchema.sql* erstellt. Der Listener der Datenbank wird am Standardport 3306 erwartet. Die Applikation verwendet den Benutzer *smartvalapi*, dieser ist mit allen Berechtigungen bereitzustellen. Abweichende Konfigurationen, wie auch das Passwort sind in der Datei *application.properties* zu hinterlegen.

### MongoDB
In der ausgelieferten Version erwartet SmartValAPI, dass der Import von Messdaten in der Datenbank *meterData* in der Collection *meterTable erfolgte*. Der Benutzername, mit dem Der Zugriff erfolgt lautet, wie für die SQL Datenbank *smartvalapi*. Für die NoSQLDatenbank können alle Konfigurationsparameter in der Datei den Notwendigkeiten entsprechend angepasst werden.

Hier die application.properties einfügen, die es betrifft: MongoServer noch hierherverlagern:
```sh
# DataSource settings: set here your own configurations for the database
# connection. In this example we have "netgloo_blog" as database name and
# "root" as username and password.
spring.datasource.url = jdbc:mysql://localhost:3306/smart_meter
spring.datasource.username = smartvalapi
spring.datasource.password = smartval

# Keep the connection alive if idle for a long time (needed in production)
spring.datasource.testWhileIdle = true
spring.datasource.validationQuery = SELECT 1
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
#spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# Show or not log for each sql query
spring.jpa.show-sql = true

# Hibernate ddl auto (create, create-drop, update)
spring.jpa.hibernate.ddl-auto = update

# Naming strategy
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy

# Use spring.jpa.properties.* for Hibernate native properties (the prefix is
# stripped before adding them to the entity manager)

# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
log4j.logger.org.springframework.jdbc.core = TRACE
```

## Auslieferung des Softwarepakets
Eine Installation von SmartValAPI steht unter folgender Url zur Verfügung: 
``` 
http://landsteiner.fh-salzburg.ac.at:8080
``` 
## Beispielzugriff
Dieser Zugriff muss erst im Abschlussdokument eingefügt werden, ausserdem sollen die Bilder vom Vergleichen der angeglichenen Messdatenreiheneingefügt werden.
Ausserdem gehört das ViererBild dazu, um zu demonstrieren, dass die Auflösung mit einem Zugriff geregelt werden kann.
``` 
/admin/customer/1 und so weiter, das gehört noch angepasst.
``` 