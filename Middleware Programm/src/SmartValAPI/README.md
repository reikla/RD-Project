![N|Solid](https://www.fh-salzburg.ac.at/fileadmin/templates/img/FH_Salzburg_Logo_140.png)

# SmartValAPI - Installationsanleitung 
Dieses Dokument beschreibt die Installation von SmartValAPI. Weiters werden die Systemvoraussetzungen angeführt. 
## Systemvoraussetzungen
hol den Inhalt aus der Prosa im Abschlussdokument.

```sh
so macht man Befehle
```

## Installation 
auch dieses Kapitel ist im Abschlussdokument angeführt.

## Konfiguration
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

## Beispielzugriff
Dieser Zugriff muss erst im Abschlussdokument eingefügt werden, ausserdem sollen die Bilder vom Vergleichen der angeglichenen Messdatenreiheneingefügt werden.
Ausserdem gehört das ViererBild dazu, um zu demonstrieren, dass die Auflösung mit einem Zugriff geregelt werden kann.

## Erweiterung MongoDB
Erwähne welche Zugriffe, welche Konfigurationsgeschichten angepasst werden müssen, es geht dabei um die 

**Landsteiner rulez**, spricht aber nicht mehr mit mir. 