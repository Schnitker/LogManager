package com.github.schnitker.logmgr.log4j2;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * ServletContainerInitializer for web application greater/equal 3.0 only.
 */
public class JulServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        // test 3.0 environment and isLog4jAutoInitializationDisabled
        if (ctx.getMajorVersion() > 2 && ctx.getEffectiveMajorVersion() > 2 &&
                !"true".equalsIgnoreCase(ctx.getInitParameter( "isLog4jAutoInitializationDisabled" ))) {

            ctx.addListener(new JulServletContextListener());
        }
    }

}
