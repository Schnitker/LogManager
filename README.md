LogManager
==========

JDK LogManager implementations for Log4j and Log4j2. Alternative to SLF4JBridgeHandler.

### Usage

*   Set system property in main method: <br/>
    ``` System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() ); ```

*   Set system property on command line: <br/>
    ``` java -Djava.util.logging.manager=com.schnitker.logmgr.log4j2.JulLogManager.class ```

### Eclipse Setup

run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'
