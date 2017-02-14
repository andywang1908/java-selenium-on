package ca.on.gov.gsc.s2i.e2e.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class E2EUtil {
	public static E2EUtil util;
	public static E2EUtil getInstance(WebDriver in) {
		if ( util==null ) {
			util = new E2EUtil();
			util.driver = in;
		}
		return util;
	}
	private WebDriver driver;

	public WebElement fluentWait(final By locator) {
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	            .withTimeout(30, TimeUnit.SECONDS)
	            .pollingEvery(5, TimeUnit.SECONDS)
	            .ignoring(NoSuchElementException.class);

	    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
	        public WebElement apply(WebDriver driver) {
	            return driver.findElement(locator);
	        }
	    });
	    
	    /*
		boolean flag = true;
		while (flag) {
			try {
				WebElement el = driver.findElement(By.name("q"));
				if (el != null) // Check if the required date element is found
								// or not
				{
					System.out.println("found...");
					flag = false;
				}
			} catch (Exception e) { // Catches exception if no element found
				try {
					System.out.println("wait...");
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}*/

	    return  foo;
	};

	public void js(String script) {
		JavascriptExecutor js;
		if (driver instanceof JavascriptExecutor) {
		    js = (JavascriptExecutor)driver;
		    //System.out.println( "script:"+script );
			js.executeScript(script);
		}
	}
	public void jsInput(String name,String value) {
		if (value==null) return;
		
		String script;

		script = "var name=\""+name+"\";var value=\""+value+"\";$('input[name=\"'+name+'\"]').val(value);$('input[name=\"'+name+'\"]').blur();";
		//this.log(script);

		this.js(script);
	}
	public void jsInputById(String name,String value) {
		String script;

		//TODO be careful with special letter "
		script = "var name=\""+name+"\";var value=\""+value+"\";$('input[id=\"'+name+'\"]').val(value);";
		//this.log(script);

		this.js(script);
	}
	public void jsSelect(String name,String value) {
		String script;

		script = "var name=\""+name+"\";var value=\""+value+"\";$('select[name=\"'+name+'\"]').val(value);";
		//this.log(script);

		this.js(script);
	}
	public void jsSelectById(String name,String value) {
		String script;

		script = "var name=\""+name+"\";var value=\""+value+"\";$('select[id=\"'+name+'\"]').val(value);";
		//this.log(script);

		this.js(script);
	}
	public void jsBottom() {
		String script;

		script = "window.scrollTo(0,document.body.scrollHeight);";
		//this.log(script);

		this.js(script);
	}
	public void radioById(String name) {
		/*
		String script;
		script = "$('input[name=\""+name+"\"][value="+value+"]').click();$('input[name=\""+name+"\"][value="+value+"]').focus();$('input[name=\""+name+"\"][value="+value+"]').blur();";
		this.js(script);*/
		List<WebElement> elementList;

		//org.openqa.selenium.StaleElementReferenceException: stale element reference: element is not attached to the page document
		//http://seleniumhq.org/exceptions/stale_element_reference.html

		elementList = driver.findElements(By.id(name));
		for(WebElement elementSingle : elementList) {
			//element.click();
			try {
				elementSingle.click();
			} catch (StaleElementReferenceException e) {
				elementList = driver.findElements(By.id(name));
				for(WebElement elementSingle1 : elementList) {
					elementSingle1.click();
				}
				break;
			}
		}
	}
	//first? js click is not equal
	//two? sometimes need more time
	public void radio(String name,String value) {
		/*
		String script;
		script = "$('input[name=\""+name+"\"][value="+value+"]').click();$('input[name=\""+name+"\"][value="+value+"]').focus();$('input[name=\""+name+"\"][value="+value+"]').blur();";
		this.js(script);*/
		List<WebElement> elementList;
		String attr;

		//org.openqa.selenium.StaleElementReferenceException: stale element reference: element is not attached to the page document
		//http://seleniumhq.org/exceptions/stale_element_reference.html

		elementList = driver.findElements(By.name(name));
		for(WebElement elementSingle : elementList) {
			//element.click();
			try {
				attr = elementSingle.getAttribute("value");
				if ( attr.equals(value) ) {
					elementSingle.click();
					break;
				}
			} catch (StaleElementReferenceException e) {
				elementList = driver.findElements(By.name(name));
				for(WebElement elementSingle1 : elementList) {
					//element.click();
					attr = elementSingle1.getAttribute("value");
					if ( attr.equals(value) ) {
						elementSingle1.click();
						break;
					}
				}
				break;
			} catch (ElementNotVisibleException e) {
				//e.printStackTrace();
				System.out.println("Got ElementNotVisibleException");
				return;
			}
		}
	}
	public void jsRadio(String name,String value) {
		this.radio(name, value);
	}
	public void jsCheckbox(String name,String value) {
		this.radio(name, value);
	}
	public void jsDate(String name,String value) throws InterruptedException {
		//List<WebElement> elementList;

		String script;

		//alert($('input[name=\""+name+"\"]').length);
		script  = "$('input[name=\""+name+"\"]').focus();";
		script += "$('input[name=\""+name+"\"]').val('"+value+"');";
		script += "$('input[name=\""+name+"\"]').blur();";
		script += "$('.ui-datepicker').hide();";
		//this.js("$('input[name=\"supord.date\"]').val('"+this.jsonString(jsonObject, field)+"');");
		//this.js("$('select[name=\"supord.province\"]').val('"+"AB"+"');");
		//this.js("$('input[name=agreeDate]').click();");
		//driver.wait(1000);

		this.js(script);
	}
	public void jsAlert() throws InterruptedException {
		Thread.sleep(500);
		try {
			Alert alert = driver.switchTo().alert();
			if ( alert!=null ) {
				alert.accept();
				//Thread.sleep(500);//wait between alert
			}
		} catch (Exception e) {

		}
	}


	public void log(Object msg) {
		System.out.println(msg);
	}
	public String formatDate(String source) {
		String re=source;
		if ( re==null ) return null;

		if ( "near".equals(source) ) {
			//re = "08/15/2015";
			re = "2015-08-15";
		} else if ( "far".equals(source) ) {
			//re = "08/15/2012";
			re = "2012-08-15";
		}
		return re;
	}

	public String jsonDate(JSONObject jsonObject,String field) {
		String re = null;
		try {
			re = jsonObject.getString(field);
		} catch (Exception e) {
			return null;
		}
		re = this.formatDate(re);
		return re;
	}

	public String jsonString(JSONObject jsonObject,String field) {
		String re = null;
		try {
			re = jsonObject.getString(field);
			
			//TODO deal more special letter
			//re = re.replaceAll("\\u", "'");
		} catch (Exception e) {
		}
		return re;
	}
}
