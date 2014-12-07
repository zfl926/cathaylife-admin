package com.cathaylife.admin.webapp.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackListener implements ServletContextListener {
	
	private static final String CONFIG_LOCATION = "logbackConfigLocation";

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
		String logbackConfigLocation = ctx.getServletContext().getInitParameter(CONFIG_LOCATION);  
        String fn = ctx.getServletContext().getRealPath(logbackConfigLocation); 
        
        try {
        	LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();  
        	loggerContext.reset();  
        	JoranConfigurator joranConfigurator = new JoranConfigurator();  
        	joranConfigurator.setContext(loggerContext);  
			joranConfigurator.doConfigure(fn);
		} catch (JoranException e) {
			throw new RuntimeException(e);
		}
        
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		
	}

}
