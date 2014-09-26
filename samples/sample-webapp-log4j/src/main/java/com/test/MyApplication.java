package com.test;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath( "/" )
public class MyApplication extends ResourceConfig {

    public MyApplication () {
        packages( "com.text" );
    }
}
