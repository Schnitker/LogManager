package com.github.schnitker.logmgr.log4j.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.TEXT_PLAIN)
public class Welcome {

    @GET
    public String get() {
        return "Welcome page (log4j)";
    }
}
