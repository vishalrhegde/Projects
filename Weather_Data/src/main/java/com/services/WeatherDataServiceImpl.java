package com.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.util.ConfigProperties;

public class WeatherDataServiceImpl implements WeatherDataService{
	
	private static final Logger logger = Logger.getLogger(WeatherDataServiceImpl.class);
	
	private String citiesArray[] = null;
	private String outputFilePath = null;
	private String finalResult = "Station | Conditions | Temperature | Pressure | Humidity";
	
	public void generateWeatherInfo() throws Exception {
		
		init();		
		
		String wURL = ConfigProperties.getInstance().getProperties().getProperty("weather_url");		
		wURL = wURL.replace("@", ConfigProperties.getInstance().getProperties().getProperty("country"));
		
		if(citiesArray != null){			
			for(String city : citiesArray){
				String finalURL = wURL.replace("#", city);
				logger.debug("Final URL: " + finalURL);
				URL urlOBJ = new URL(finalURL);
				
				HttpURLConnection conn = (HttpURLConnection) urlOBJ.openConnection();
				conn.setRequestMethod("GET");				
				conn.setRequestProperty("User-Agent", "Mozilla/5.0");

				int responseCode = conn.getResponseCode();
				if(responseCode == 200){
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));					
					StringBuffer response = new StringBuffer();
					
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					logger.debug("Response: " + response.toString());	
					Gson gson = new Gson();
					
			    	JsonObject jObj = gson.fromJson(response.toString(), JsonObject.class);
			    
			    	JsonObject jObj1 = gson.fromJson(jObj.getAsJsonArray("weather").get(0).toString(), JsonObject.class); 
			    	String conditions = jObj1.get("description").toString();
			    	conditions = conditions.replaceAll("\"", "");
			    	
			    	String temperature = getTemperature(jObj.getAsJsonObject("main").get("temp").toString());
			    	String pressure = jObj.getAsJsonObject("main").get("pressure").toString();
			    	String humidity = jObj.getAsJsonObject("main").get("humidity").toString();			    	
			    	  	
			    	finalResult = finalResult + "\n" + city + " | " + conditions + " | " + temperature + " | " + pressure + " | " + humidity;
				}				
			}
			writeToFile();
		}		
	}
	
	private void init(){
		//1.
		String citiesStr = ConfigProperties.getInstance().getProperties().getProperty("cities");		
		citiesArray = citiesStr.split(",");	
		logger.debug("Cities read from config file: " + citiesArray);
		//2.
		outputFilePath = ConfigProperties.getInstance().getProperties().getProperty("outputFilePath");
	}
	
	private String getTemperature(String temp){
		Float fTemp = Float.parseFloat(temp) - 273;
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(fTemp);		
	}
	
	private void writeToFile(){
		Writer writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"));
		    writer.write(finalResult);
		} catch (IOException ex) {
			logger.error(ex.getStackTrace());
		} finally {
		   try {			 
			   writer.close();
		   } catch (Exception ex) {
			   logger.error(ex.getStackTrace());
		   }
		}
	}

}
