package com.github.schnitker.logmgr.log4j.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class Hello {

    @GET
    public String get() {
        return "hello log4j";
    }
}
