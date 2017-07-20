package com.fanniemae.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.xerces.impl.validation.ConfigurableValidationState;
import org.openqa.selenium.WebDriver;
import org.testng.internal.ConfigurationGroupMethods;
import org.testng.log4testng.Logger;

public class TestBaseMine {

	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties config = new  Properties();
	//public static Logger log = Logger.getLogger("devpinoyLogger");
	
	
	
	public void setUp() {
		
		if (driver == null){
			
			try{
				fis = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\properties\\Config.properties");
				
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			
			try{
				config.load(fis);
			//log.debug("Config fileLoaded");		
			}catch (IOException e){
				e.printStackTrace();
			}
			
			
		}
			

	}

	public void tearDown() {

	}

}
