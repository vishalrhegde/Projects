package com.main;

import org.apache.log4j.Logger;

import com.services.WeatherDataService;
import com.services.WeatherDataServiceImpl;

/**
 * 
 * @author Vishal
 * 
 * Use this class for running in a batch mode. Create a batch/sh file and give this class as part of java args.
 *
 */
public class WeatherDataMain {
	
	private static final Logger logger = Logger.getLogger(WeatherDataMain.class);
	
	public static void main(String args[]){
		
		WeatherDataService wdService = new WeatherDataServiceImpl();
		try {
			wdService.generateWeatherInfo();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
			
}
