LogManager
==========

JDK LogManager implementations for Log4j, Log4j2 and Logback.

### Introduction

My favorite Framework Jersey (JAX-RS 2.0) uses JDK logging. There are some disadvantages with JDK Logging:

 * without configuration the logging output will be written to stderr
 * the handler SLF4JBridgeHandler has performance impacts (see http://www.slf4j.org/api/org/slf4j/bridge/SLF4JBridgeHandler.html)
 
This project allows you to redirect JDK logging to Log4j (1.x or 2.x) or Logback without performance costs.
The Jersey Grizzly HTTP server (jersey-container-grizzly2-http) works fine with one of the wrapper implementations.

### Usage (for applications)

*   Set system property in main method: <br/>
    ``` System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() ); ```

*   Set system property on command line: <br/>
    ``` java -Djava.util.logging.manager=com.github.schnitker.logmgr.log4j.JulLogManager ```

### Servlet container

Note that the JDK LogManager does not work well with servlet containers. 

There is an context class loader friendly implementation in the module 'logmgr-factory'. The factory searches through 
the Java ServiceLoader for an implementation of 'com.github.schnitker.logmgr.JulLoggerFactory'. The module 'logmgr-factory'
must be added to the bootstrap libraries and the system propety 'java.util.logging.manager' must be set.

Every web application instance can contain one logging wrapper.

<pre>
          Bootstrap                    </br>
          (logmgr-factory)             </br>
                                         </br>
|    Webapp1       |   Webapp2 ...      |</br>
|  (logmgr-log4j)  |   (logmgr-logback) |
</pre>
  
##### Tomcat
 * Eclipse start: Add the VM argument "-Djava.util.logging.manager=com.github.schnitker.logmgr.JulLogManager" and the 
   logmgr-factory jar file in the run or debug configuration.
 * Installation: You must extend the CLASSPATH environment variable (add logmgr-factory.jar) and set the LOGGING_MANAGER environment 
   variable to "com.github.schnitker.logmgr.JulLogManager" in the catalina shell script.

### Maven Repository

Add one of the artifacts:

*   Log4j 1.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j:1.0.1' ```

*   Log4j 2.x implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-log4j2:1.0.1' ```

*   Logback implementation: <br/>
    ``` 'com.github.schnitker.logmanager:logmgr-logback:1.0.1' ```


### Gradle

The project uses the gradle build system.
 
 * **Eclipse Setup**: Run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'.

 * **Building artifacts**: Call 'gradlew assemble'.
