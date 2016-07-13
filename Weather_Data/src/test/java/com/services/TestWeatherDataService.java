package com.services;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class TestWeatherDataService extends TestCase{
	
	private static final Logger logger = Logger.getLogger(TestWeatherDataService.class);
	
	public final void testGetWeatherInfo(){
		WeatherDataService wdService = new WeatherDataServiceImpl();
		try {
			wdService.generateWeatherInfo();
		} catch (Exception e) {	
			logger.error(e.getStackTrace());			
		}
	}

}
