LogManager
==========

JDK LogManager implementations for Log4j and Log4j2.

My favorite Framework Jersey (JAX-RS 2.0) uses JDK logging. Without configuration the logging output will be written to stderr.
This project allows you to redirect JDK logging to Log4j (1.x or 2.x) without performance costs (see SLF4JBridgeHandler).

### Usage

*   Set system property in main method: <br/>
    ``` System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() ); ```

*   Set system property on command line: <br/>
    ``` java -Djava.util.logging.manager=com.github.schnitker.logmgr.log4j.JulLogManager ```

### Eclipse Setup

run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'
