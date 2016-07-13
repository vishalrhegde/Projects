package com.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigProperties {
	private static final Logger logger = Logger.getLogger(ConfigProperties.class);
	
	private ConfigProperties(){		
		properties = new Properties();
		InputStream in = null;
		try {
			logger.debug("Loading Properties File ..... ");
			in = getClass().getClassLoader().getResourceAsStream("conf/config.properties");
            properties.load(in);
			if(properties != null) {
				logger.debug("config.properties loaded successfully");
			} else {
				logger.debug("config.properties does not exist or not configured correctly");
			}
		} catch (Exception e) {			
			logger.error(e.getStackTrace());
		}
	}
	
	public static ConfigProperties getInstance(){
		if(instance == null){
			instance = new ConfigProperties();
		}
		return instance;
	}
	

	public Properties getProperties() {
		return properties;
	}
	

	private static ConfigProperties instance = null;
	private Properties properties = null;
	
}
