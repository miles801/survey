<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" >
    <parent >
        <artifactId >eccrm-${module}</artifactId >
        <groupId >eccrm</groupId >
        <version >1.0.0-SNAPSHOT</version >
        <relativePath >../pom.xml</relativePath >
    </parent >
    <modelVersion >4.0.0</modelVersion >
    <artifactId >${module}-web</artifactId >

    <dependencies >
        <dependency >
            <groupId >eccrm</groupId >
            <artifactId >${module}-impl</artifactId >
            <version >1.0.0-SNAPSHOT</version >
        </dependency >
        <dependency >
            <groupId >com.google.code.gson</groupId >
            <artifactId >gson</artifactId >
        </dependency >
        <dependency >
            <groupId >org.apache.tomcat</groupId >
            <artifactId >servlet-api</artifactId >
            <scope >provided</scope >
        </dependency >
        <dependency >
            <groupId >jstl</groupId >
            <artifactId >jstl</artifactId >
        </dependency >

        <dependency >
            <groupId >junit</groupId >
            <artifactId >junit</artifactId >
            <scope >test</scope >
        </dependency >
        <dependency >
            <groupId >org.springframework</groupId >
            <artifactId >spring-test</artifactId >
            <scope >test</scope >
        </dependency >
    </dependencies >
</project >