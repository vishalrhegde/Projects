package com.services;

public interface WeatherDataService {
	
	/**
	 * Connect to the Thirdparty Weather App and get all the Weather Info. 
	 * @return
	 * @throws Exception
	 */
	public void generateWeatherInfo() throws Exception;
	
}
