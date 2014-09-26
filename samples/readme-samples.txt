
Eclipse + Tomcat:
-------------------

 * requires Tomcat 7  (defined in build file 'sample-webapp.gradle')
 
 * requires assembled libraries ( run 'gradlew assemble') 
 
 * generate eclipse file (run eclipseInit) 
 
 * Run configuration 
   - Add VM argument -Djava.util.logging.manager=com.github.schnitker.logmgr.JulLogManager 
   - Add classpath user library: add "logmgr-factory" project or jar file

 