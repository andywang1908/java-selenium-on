package ca.on.gov.gsc.s2i.e2e.acsc.scenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.acsc.PropertyACSC;
import ca.on.gov.gsc.s2i.e2e.acsc.SeleniumACSC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;

public class Parent2RecipientTest extends SeleniumACSC {
	public Parent2RecipientTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/*keep close*/
    @Parameters
    public static Collection<String[]> addParamter() {
    	return Arrays.asList(new String[][] { { "ACSC-P2Re-000", "close" } });
    }

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);
		
		automationConfig.setRootUrl( PropertyACSC.getInstance().getProperty("s2i.acsc.rootUrl2") );
		
		mode = 0;
		//resultId = "Same Address";
		jsonFile = "Parent2 Recipient";
		//jsonFile = "Fee Waiver";
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
		this.fillApplicationFee(jsonObject);
		this.fillApplicationFileUpload(jsonObject);
		
		//check
		this.checkPageError();
		summary += checkApplicationFee(jsonObject, driver, e2EUtil);

		this.fillApplicationSubmit(jsonObject);
		
		summary += checkApplicationSubmit(jsonObject, driver, e2EUtil);

		return summary;
	}

	protected void fillEligibility(JSONObject jsonObject) throws InterruptedException {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		//first page can not check page because of restart
		//this.checkPageError();
		
		try {
			jsonObject.getString("identificationNumber");
			jsonModel = jsonObject;
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		e2EUtil.fluentWait(By.name("refNum"));
		
		if ( !gotIt ) {
			e2EUtil.jsInput("refNum","2222RAT");

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			this.waitIt();

			e2EUtil.jsRadio("optOut", "true");
			e2EUtil.jsRadio("taxIsFiled", "true");

			elementList = driver.findElements(By.className("s2iNavigateAway"));
			if ( elementList.size()>0 ) {
				element = elementList.get(0);
				element.click();
			}
		} else {
			key = "identificationNumber";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("refNum", value);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			this.waitIt();

			key = "proceed";
			value = e2EUtil.jsonString(jsonModel, key);
			value = "true";
			e2EUtil.jsRadio("optOut", value);
			/**/
			key = "optReason";
			value = e2EUtil.jsonString(jsonModel, key);
			//value = "2&13-5";
			//TODO function "&"
			String[] array = value.split("&");
			for ( String arraySingle: array) {
				if ( !arraySingle.equals("") ) {
					String[] arraySub = arraySingle.split("-");
					if ( arraySub.length==1 ) {
						e2EUtil.jsCheckbox("optOutReasons["+arraySingle+"].selected", "true");
					} else {
						e2EUtil.jsCheckbox("optOutReasons["+arraySub[0]+"].subreasons["+arraySub[1]+"].selected", "true");
					}
				}
			}
			key = "taxIsFiled";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
						
			elementList = driver.findElements(By.className("s2iNavigateAway"));
			if ( elementList.size()>0 ) {
				element = elementList.get(0);
				element.click();
			}
		}

		//this.waitIt();
		//element = e2EUtil.fluentWait(By.className("s2iNavigateAway"));
	}

	protected void fillApplicationParent(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		
		this.checkPageError();
		
		try {
			jsonObject.getString("sin");
			jsonModel = jsonObject;
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		//e2EUtil.fluentWait(By.name("applicant.SIN"));
		
		if ( !gotIt ) {
			//field = "agreeDate";
			e2EUtil.jsInput("applicant.SIN","000000018");
			e2EUtil.jsDate("applicant.DOB", e2EUtil.formatDate("1975-10-10"));
			//field = "childPer";
			e2EUtil.jsRadio("applicant.preferedLanguage", "en");
			e2EUtil.jsRadio("receiveUCCB", "false");
		} else {
			key = "sin";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.SIN",value);

			key = "birthday";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsDate("applicant.DOB", e2EUtil.formatDate(value));
			
			key = "email";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.email",value);

			key = "language";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio("applicant.preferedLanguage", value);

			key = "benefit";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio("receiveUCCB", value);
			
			key = "benefitAmt";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("uccbAmount",value);
		}
		
		//apple+tv  receiver+speaker
		//penium         save some tv          watch tv/stock green
		//i5             andy backup           game/wangyin vm
		//z97            mary download
		
		//browser.focus("css=a.indexTwitter");
		/*
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File("c:/tmp/screenshot.png"));*/
		
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}
}
