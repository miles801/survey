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

    <groupId >eccrm</groupId >
    <artifactId >${module}-api</artifactId >
    <dependencies >
        <dependency >
            <groupId >eccrm</groupId >
            <artifactId >eccrm-core</artifactId >
            <version >1.0.0-SNAPSHOT</version >
        </dependency >

        <dependency >
            <groupId >eccrm</groupId >
            <artifactId >base-api</artifactId >
            <version >1.0.0-SNAPSHOT</version >
        </dependency >
    </dependencies >

</project >