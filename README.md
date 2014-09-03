LogManager
==========

JDK LogManager implementations for Log4j and Log4j2. Alternative to SLF4JBridgeHandler.


Installation:

  * set system property in main method :
    System.setProperty( "java.util.logging.manager", JulLogManager.class.getName() );

  * set system property on command line:
    System.setProperty( "java.util.logging.manager", "com.schnitker.logmgr.log4j2.JulLogManager.class" );
