LogManager
==========

JDK LogManager implementations for Log4j, Log4j2 and Logback.

My favorite Framework Jersey (JAX-RS 2.0) uses JDK logging. Without configuration the logging output will be written to stderr.
This project allows you to redirect JDK logging to Log4j (1.x or 2.x) or Logback without performance costs (see SLF4JBridgeHandler).

Note that the JDK LogManager does not work well with servlet containers. The Jersey Grizzly HTTP server (jersey-container-grizzly2-http) 
works fine.

There is an context class loader implementation in the module 'logmgr-factory'. The factory searches through the ServiceLoader 
for a implementation of 'com.github.schnitker.logmgr.JulLoggerFactory'. Therefore you must provide the module 'logmgr-factory' 
and the logging implementation (logmgr-log4j or logmgr-logback) in the container library bootstrap folder (f.e. tomcat/lib). 
The system property 'java.util.logging.manager' must be set to the logmgr-factory implementation.</br>

Every web application instance can contain one logging wrapper.

### Usage

*   Set system property in main method: <br/>
    ``` System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() ); ```

*   Set system property on command line: <br/>
    ``` java -Djava.util.logging.manager=com.github.schnitker.logmgr.log4j.JulLogManager ```

### Maven Repository

Add one of the artifacts:

*   Log4j 1.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j:1.0.1' ```

*   Log4j 2.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j2:1.0.1' ```

*   Logback implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-logback:1.0.1' ```

### Eclipse Setup

run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'
