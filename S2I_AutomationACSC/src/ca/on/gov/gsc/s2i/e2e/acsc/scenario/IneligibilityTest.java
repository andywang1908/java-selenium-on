package ca.on.gov.gsc.s2i.e2e.acsc.scenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.acsc.SeleniumACSC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;

public class IneligibilityTest extends SeleniumACSC {
	public IneligibilityTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/* "ACSC-Ineli-0003" 
    @Parameters
    public static Collection<String[]> addParamter() {
        return Arrays.asList(new String[][] { { "ACSC-Ineli-0188", "keep" } });
    }*/

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//mode = 0;
		//resultId = "Same Address";
		jsonFile = "Ineligibility";
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String key;
		String value;

		String summary = "";
		
		boolean checkMessageFlag = true;

		this.fillEligibility(jsonObject);
		
		this.checkPageError();
		
		elementList = driver.findElements(By.id( fixNameSpace("nonEligibleForm") ));
		if ( elementList.size()==0 ) {
			throw new CheckException("This test data can not pass the Eligibility Check!");
		}

		String summaryThis = "";
		elementList = driver.findElements( By.className("Instruction") );
		//System.out.println("elementList:"+elementList.size());
		for (WebElement elementSingle:elementList) {
			summaryThis += elementSingle.getText()+"<br/>";
		}
		summary += summaryThis;
		
		if ( checkMessageFlag ) {
			value = "";
			try {
				JSONObject jsonModel = jsonObject.getJSONObject("eligibility");

				key = "message";
				value = e2EUtil.jsonString(jsonModel, key);
			} catch ( Exception e ) {
			}
			String summaryExpect = value;
			
			if ( !"".equals(summaryExpect) ) {
				if ( !this.checkMessage(summaryExpect,summaryThis) ) {
					throw new CheckException("Error Message Check fail!expect:("+summaryExpect+").actually:("+summaryThis+")");
				}
			}
		}

		return summary;
	}
	
	private String convertMessage(String source) {
		String result = "";
		source = source.toLowerCase();
		result = source.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("<br/>", "").replaceAll("<br>", "");
		
		return result;
	}

	private boolean checkMessage(String summaryExpect, String summaryThis) {
		// TODO Auto-generated method stub
		String summaryExpectShort = convertMessage(summaryExpect);
		String summaryThisShort = convertMessage(summaryThis);
		if ( summaryExpectShort.equals(summaryThisShort) ) {
			return true;
		}
		
		return false;
	}

}
