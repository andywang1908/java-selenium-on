package ca.on.gov.gsc.s2i.e2e.acsc.scenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.acsc.SeleniumACSC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;
import ca.on.gov.gsc.s2i.e2e.util.E2EUtil;

public class PaymentTest extends SeleniumACSC {
	public PaymentTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/*keep close  02 15 true-c-43
    @Parameters
    public static Collection<String[]> addParamter() {
    	return Arrays.asList(new String[][] { { "ACSC-P1-0011", "keep" } });//32 38 44
    }*/

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//&simulator=true
		automationConfig.setRootUrl( "https://stage.services1.gov.on.ca/sodp/poc/s2c?uri=s2i:ACSC000P1Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q" );

		mode = 0;
		//resultId = "Same Address";
		jsonFile = "Payment";
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		//String key;
		//String value;

		String summary = "";

		this.fillEligibility(jsonObject);
		
		elementList = driver.findElements(By.id( fixNameSpace("nonEligibleForm") ));
		if ( elementList.size()>0 ) {
			throw new CheckException("This test data should pass the Eligibility Check!");
		}

		this.fillApplicationParent(jsonObject);
		this.fillApplicationChild(jsonObject);
		this.fillApplicationExpense(jsonObject);
		this.fillApplicationFee(jsonObject);
		this.fillApplicationFileUpload(jsonObject);
		
		//check
		this.checkPageError();
		summary += checkApplicationFee(jsonObject, driver, e2EUtil);

		this.fillApplicationSubmit(jsonObject);
		
		summary += checkApplicationSubmit(jsonObject, driver, e2EUtil);

		return summary;
	}

	//ignore this.checkPageError
	protected String checkApplicationSubmit(JSONObject jsonObject, WebDriver driver, E2EUtil e2EUtil) throws Exception {
		String result = "";
		WebElement element = null;
		List<WebElement> elementList;

		//this.checkPageError();
		
		try {
			e2EUtil.fluentWait(By.cssSelector(".Data div"));
			
			//element = driver.findElement(By.cssSelector(".Data div"));
		} catch (Exception e) {
			throw new CheckException("Can not go to last page");
		}
		
		elementList = driver.findElements(By.cssSelector(".Data div"));
		String str = "";
		String transId = "";
		for (int i=0;i<elementList.size();i++) {
			str = elementList.get(i).getText();
			result += str+"<br/>";
			if ( str.indexOf("Reference number:")>=0 ) {
				transId = str.substring( str.indexOf("Reference number:")+"Reference number:".length() );
			}
		}
		
		return result;
	}

	/*
	protected void checkPageError() throws Exception {
		Thread.sleep(500);

		List<WebElement> elementList = driver.findElements(By.cssSelector(".Data div"));
		if ( elementList.size()>0 ) {
			String summary = "";
			for( WebElement elementSingle : elementList ) {
				if ( elementSingle.getText()!=null && !elementSingle.getText().equals("") ) {
					summary += elementSingle.getText()+"<br/>";
				}
			}
			
			if ( !summary.equals("") ) {
				throw new CheckException(summary);
			}
		}
		//Errors
	}*/
	
	protected void fillApplicationSubmit(JSONObject jsonObject) throws InterruptedException {
		JSONObject jsonModel = jsonObject;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		//key = "liquid";
		//value = e2EUtil.jsonString(jsonModel, key);
		e2EUtil.jsCheckbox("certified", "true");

		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();

		this.waitIt();
		//this.waitIt();
		elementList = driver.findElements(By.name("paymentType"));
		if ( elementList.size()>0 ) {
			//pay money
			key = "paymentType";
			value = e2EUtil.jsonString(jsonModel, key);
			String paymentType = value;//value  Interac   CreditCard
			e2EUtil.jsRadio(key, paymentType);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();

	        e2EUtil.js("$('#confirmPaymentButton').click();");

	        Thread.sleep(1000);
			e2EUtil.jsAlert();
	        Thread.sleep(1000);

	        if ( "CreditCard".equals(paymentType) ) {
				e2EUtil.jsInput("trnCardOwner", "andy");
				e2EUtil.jsInput("trnCardType", "MC");
				key = "paymentAmount";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsInput("trnCardNumber", value);
				e2EUtil.jsSelect("trnExpYear", "18");
				e2EUtil.jsInput("trnCardCvd", "123");

				element = driver.findElement(By.name("submitButton"));
				element.click();

				e2EUtil.jsAlert();

				element = driver.findElement(By.name("submitButton"));
				element.click();
	        } else {
				element = e2EUtil.fluentWait(By.name("btnBank"));
				element.click();

				element = e2EUtil.fluentWait(By.name("btnLogin"));
				element.click();
				
				element = e2EUtil.fluentWait(By.name("btnsend"));
				element.click();
				
				element = e2EUtil.fluentWait(By.name("btnnext"));
				element.click();
				
				System.out.println("debit card can be empty");
	        }
		}
		
		//for wait
		/**/
		String consent = e2EUtil.jsonString(jsonModel, "downloadConsent");
		if ( "true".equals(consent) ) {
			try {
				e2EUtil.fluentWait(By.name("printOrEmail"));
				e2EUtil.jsCheckbox("printOrEmail", "email");

				element = driver.findElement(By.className("s2iNavigateAway"));
				element.click();

		        Thread.sleep(500);
				e2EUtil.jsAlert();
				
			} catch (Exception e) {
				throw new CheckException("There is no consent returned!");
			}
		} else if ( "false".equals(consent) ) {
			try {
				e2EUtil.fluentWait(By.id("acsc_id-email"));
			} catch (Exception e) {
				elementList = driver.findElements(By.name("printOrEmail"));
				if ( elementList.size()>0 ) {
					throw new CheckException("There is consent returned!");
				} else {
					throw new CheckException("Can not go to confirmation page!");
				}
			}
			
		} else {
			try {
				e2EUtil.fluentWait(By.name("printOrEmail"));
				e2EUtil.jsCheckbox("printOrEmail", "email");

				element = driver.findElement(By.className("s2iNavigateAway"));
				element.click();
			} catch (Exception e) {
				System.out.println("There is no email send page.");
			}
		}

	}

}
