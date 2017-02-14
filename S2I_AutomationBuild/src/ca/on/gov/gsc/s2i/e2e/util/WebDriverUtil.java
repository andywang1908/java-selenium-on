package ca.on.gov.gsc.s2i.e2e.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;

public class WebDriverUtil {
	private final static WebDriverUtil instance = new WebDriverUtil();
	public static WebDriverUtil getInstance() {
		return instance;
	}
	
	private Map<String,String> scenarioMap = new HashMap<String,String>();
	private AutomationConfig automationConfig;
	private WebDriver driver = null;
	
	//globalDriverType()
	//globalLocalDic()
	//1024,900
	/*
	public void initDriver(String driverType,String localDic,int windowWidth,int windowHeight) throws Exception {
		if ( driver==null ) {
			//{pageInfo:{pageName:'pageLogin'},posts:[{post_id:'1001'},{post_id:'1002'}]}
			Runtime.getRuntime().exec(localDic+"clean.bat");// cmd /c start C:/green/clean.bat
			Thread.sleep(500);
			
	    	File file;
	    	if ( "Chrome".equalsIgnoreCase(driverType) ) {
		    	file = new File(localDic+"chromedriver.exe");
		    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		    	//ChromeOptions options = new ChromeOptions();//chrome.detach
		    	driver = new ChromeDriver();
	    	} else if ( "ie".equalsIgnoreCase(driverType) ) {
		    	file = new File(localDic+"IEDriverServer.exe");
		    	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

		        driver = new InternetExplorerDriver();
	    	} else if ( "firefox".equalsIgnoreCase(driverType) ) {
		    	driver = new FirefoxDriver();
		    	//driver = new HtmlUnitDriver(true);
	    	}

	    	driver.manage().window().setPosition(new Point(0,0));
	    	driver.manage().window().setSize(new Dimension(windowWidth,windowHeight));
		}
	}*/

	/*
	public AutomationConfig initConfig() {
		AutomationConfig automationConfig = new AutomationConfig();
		return automationConfig;
	}*/

	public WebDriver initDriver() throws Exception {
		if ( driver==null ) {
			//{pageInfo:{pageName:'pageLogin'},posts:[{post_id:'1001'},{post_id:'1002'}]}
			//do not call for run multiple scenario
			Runtime.getRuntime().exec(automationConfig.getLocalDic()+"clean.bat");// cmd /c start C:/green/clean.bat
			Thread.sleep(500);
			
	    	File file;
	    	if ( "Chrome".equalsIgnoreCase(automationConfig.getDriverType()) ) {
		    	file = new File(automationConfig.getLocalDic()+"chromedriver.exe");
		    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		    	//ChromeOptions options = new ChromeOptions();//chrome.detach
		    	driver = new ChromeDriver();
		    	//driver = new HtmlUnitDriver();
		    	
	    	} else if ( "ie".equalsIgnoreCase(automationConfig.getDriverType()) ) {
		    	file = new File(automationConfig.getLocalDic()+"IEDriverServer.exe");
		    	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

		        driver = new InternetExplorerDriver();
	    	} else if ( "firefox".equalsIgnoreCase(automationConfig.getDriverType()) ) {
		    	driver = new FirefoxDriver();
		    	//driver = new HtmlUnitDriver(true);
	    	}

	    	driver.manage().window().setPosition(new Point(0,0));
	    	driver.manage().window().setSize(new Dimension(automationConfig.getWindowWidth(),automationConfig.getWindowHeight()));
		}
		
		return driver;
	}
	
	public void blockDriver(String scenarioId) {
		scenarioMap.put(scenarioId, null);
	}
	
	public void cleanDriver(String scenarioId) throws Exception {
		scenarioMap.remove(scenarioId);
		if ( scenarioMap.isEmpty() ) {
	        if ( driver!=null ) {
				//TODO save screen
				//Thread.sleep(5000);
				driver.quit();
				//driver.close();
	        }
		}
	}
	
	/**/
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public AutomationConfig getAutomationConfig() {
		return automationConfig;
	}

	public void setAutomationConfig(AutomationConfig automationConfig) {
		this.automationConfig = automationConfig;
	}
}
