LogManager
==========

JDK LogManager implementations for Log4j, Log4j2, Logback and servlet containers.

### Introduction

My favorite Framework Jersey (JAX-RS 2.0) uses JDK logging. There are some disadvantages with JDK Logging:

 * multiple logging systems if you are using log4j or logback
 * without configuration the JDK logging output will be written to stderr
 * the handler SLF4JBridgeHandler has performance impacts (see http://www.slf4j.org/api/org/slf4j/bridge/SLF4JBridgeHandler.html)
 
This project allows you to redirect JDK logging to Log4j (1.x or 2.x) or Logback without performance costs.
Configuration changes are immediately noticed by the JDK logging wrapper. The JDK LogManager and Logger are completely replaced.

The Jersey Grizzly HTTP server (_jersey-container-grizzly2-http_) works fine with all wrapper implementations.

### Usage (for applications)

*   Set system property in main method: <br/>
    ``` System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() ); ```

*   Set system property on command line: <br/>
    ``` java -Djava.util.logging.manager=com.github.schnitker.logmgr.log4j.JulLogManager ```

### Servlet container

Note that the JDK LogManager does not work well with servlet containers. 

There is an context class loader friendly implementation in the module 'logmgr-factory'. The factory searches through 
the Java ServiceLoader for an implementation of _com.github.schnitker.logmgr.JulLoggerFactory_. The module 'logmgr-factory'
must be added to the bootstrap libraries and the system property _java.util.logging.manager_ must be set.

Every web application instance can contain one logging wrapper.

<pre>
|             Bootstrap                 | </br>
|          (logmgr-factory)             | </br>
+------------------+--------------------+ </br>
|    Webapp1       |     Webapp2        | </br>
|  (logmgr-log4j)  |  (logmgr-logback)  |
</pre>
  
##### Tomcat
 * Eclipse start: Add the VM argument _-Djava.util.logging.manager=com.github.schnitker.logmgr.JulLogManager_ and add the 
   logmgr-factory jar file in the run/debug configuration.
 * Installation: You must extend the CLASSPATH environment variable (add logmgr-factory.jar) and set the LOGGING_MANAGER environment 
   variable to _com.github.schnitker.logmgr.JulLogManager_ in the catalina shell script.

### Maven Repository

Current artifacts:

*   Log4j 1.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j:1.0.2' ```

*   Log4j 2.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j2:1.0.2' ```

*   Logback implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-logback:1.0.2' ```

*   Servlet container implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-factory:1.0.2' ```


### Gradle

The project uses the gradle build system.
 
 * **Eclipse Setup**: Run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'.

 * **Building artifacts**: Call 'gradlew assemble'.
