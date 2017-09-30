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
Im Auslieferungsumfang von SmartValAPI befindet sich die übersetzte Software in Form eines Web Archives (.\src\SmartValAPI\app\build\libs\SmartValAPI-1.0.0.war). Dieses wird mit 
```
java -jar SmartValAPI-1.0.0.war
```
gestartet. Vor dem Start ist zu gewährleisten, dass alle notwendigen Dienste (LDAP, MySQL, MongoDB) gestartet wurden und verfügbar sind. 

## Konfiguration

### LDAP 
In der ausgelieferten Version geht SmartValAPI davon aus, dass ein LDAP Server am landsteiner.fh-salzburg.ac.at erreichbar ist 
Basisknoten ist dc=maxcrc,dc=com. Konkrete Details bzüglich der Hierachie der Berechtigungen und der Hierachie finden sich im Abschlussbericht.
Mögliche Rollen sind Administrator, Energieberater, Forschungszentrum, Kunde und Netzbetreiber. Diese Rollen sind als Attribute hinzuzufügen.

### MySQL 
SmartValAPI setzt ein Datenbankschema mit dem Namen *smart_meter* voraus, mit einem Benutzer, der die Berechtigungen auf diese Datenbank hat, werden das Schema, die Tabellen und Indizes mit den Skripts *DB_und_Test_Skripts/db_meters.sql* und  *DB_und_Test_Skripts/createSchema.sql* erstellt. Der Listener der Datenbank wird am Standardport 3306 erwartet. Die Applikation verwendet den Benutzer *smartvalapi*, dieser ist mit allen Berechtigungen bereitzustellen. Abweichende Konfigurationen, wie auch das Passwort sind in der Datei *application.properties* zu hinterlegen.

### MongoDB
In der ausgelieferten Version erwartet SmartValAPI, dass der Import von Messdaten in der Datenbank *meterData* in der Collection *meterTable erfolgte*. Der Benutzername, mit dem Der Zugriff erfolgt lautet, wie für die SQL Datenbank *smartvalapi*. 

## Auslieferung des Softwarepakets
Eine lauffähige Installation von SmartValAPI steht unter folgender Url zur Verfügung: 
``` 
http://landsteiner.fh-salzburg.ac.at:8080
``` 
## Beispielzugriffe
Beispielhaft werden hier zwei Zugriffe angegeben, wie sie SmartValAPI zur Verfügung stellt, für Details zu den Parametern steht das Abschlussdokument zur VErfügung.

### Adminschnittstelle
Diese Schnittstelle dient dem Administrator zum Im- und Export von Meterdaten, im Beispielfall werden die Daten des Kunden (customer-Entität) als Ergebnis zur VErfügung gestellt. Dieses Ergebnis entspricht dem Zugriff auf einen Datensatz, losgelöst von der Infrastruktur in welcher die Daten gespeichert vorliegen.
``` 
http://uri:8080/admin/customer/1 
``` 

### Benutzerschnittstelle

Die Benutzerschnittstelle dient den Applikationsprogrammen als abstrakter Zugriff, ohne die Notwendigkeit sich um die Speicherung kümmern zu müssen. Im Beispielaufruf wird auf die Daten eines Smartmeters mit der id 103 zugegriffen. Abgefragt werden Messdaten in einem Zeitraum am 1. Mai 2011, die gewünschte Auflösung beträgt eine Viertelstunde. Abhängig von der vorliegenden Auflösung und der Berechtigung kann diese Auflösung verringert ausfallen.
``` 
http://uri:8080/query/adjustedmeterdatavectors?tspvon=2011-05-01 00:00:00&tspbis=2011-05-01 05:40:00&maxSamplefreq=9000&meterIds=103
``` 
