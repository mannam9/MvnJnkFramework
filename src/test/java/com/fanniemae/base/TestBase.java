package com.fanniemae.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
//import org.testng.log4testng.Logger;
//import org.openqa.selenium.WebDriver;

//import com.fanniemae.utilities.ExtentManager;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.w2a.utilities.ExcelReader;
//import com.w2a.utilities.ExtentManager;
//import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	//public static Logger log = Logger.getLogger("devpinoyLogger");
	//public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	//public ExtentReports rep = ExtentManager.getInstance();
	//public static ExtentTest test;
	public static String browser;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				//log.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				//log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				
				browser = System.getenv("browser");
			}else{
				
				browser = config.getProperty("browser");
				
			}
			
			config.setProperty("browser", browser);
			
			
			

			if (config.getProperty("browser").equals("firefox")) {

				 System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				
			//	log.debug("FF Launched !!!");

			} else if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
			//	log.debug("Chrome Launched !!!");
			} else if (config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			//	log.debug("IE Launched !!!");

			}

			driver.get(config.getProperty("testsiteurl"));
			//log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			//driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}

	}
	
	
	
	public void click(String locator) {

		if (locator.startsWith("css_")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.startsWith("xpath_")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
			System.out.println("xpath LOCATOR: " + (OR.getProperty(locator)));
		} else if (locator.startsWith("id_")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		//test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {
		
		if (locator.startsWith("css_")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.startsWith("xpath_")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.startsWith("id_")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}else{
			System.out.println("Else Part");
		}

		//test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}
	
	static WebElement dropdown;

	public void select(String locator, String value) {

		if (locator.startsWith("css_")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.startsWith("xpath_")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.startsWith("id_")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		//test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}
	
	
	public String getText(String locator) {

		if (locator.startsWith("css_")) {
			return driver.findElement(By.cssSelector(OR.getProperty(locator))).getText();
		} else if (locator.startsWith("xpath_")) {
			return driver.findElement(By.xpath(OR.getProperty(locator))).getText();
			
		} else if (locator.startsWith("id_")) {
			return driver.findElement(By.id(OR.getProperty(locator))).getText();
		}
		//test.log(LogStatus.INFO, "Clicking on : " + locator);
		return "COULD NOT READ TEXT";
	}
	
	
	public boolean isElementPresent(By byLocator) {

		try {

			driver.findElement(byLocator);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}

	}
	
	
	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

		//log.debug("test execution completed !!!");
	}

}
