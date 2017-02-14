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

public class EligibilityTest extends SeleniumACSC {
	public EligibilityTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/* 
    @Parameters
    public static Collection<String[]> addParamter() {
        return Arrays.asList(new String[][] { { "ACSC-Ineli-0188", "keep" } });
    } */

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//mode = 0;
		//resultId = "Same Address";
		jsonFile = "Eligibility";
	}
	
	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		//String key;
		//String value;

		String summary = "";

		this.fillEligibility(jsonObject);
		
		this.checkPageError();
		
		elementList = driver.findElements(By.id( fixNameSpace("nonEligibleForm") ));
		if ( elementList.size()>0 ) {
			String summaryThis = "";
			elementList = driver.findElements( By.className("Instruction") );
			//System.out.println("elementList:"+elementList.size());
			for (WebElement elementSingle:elementList) {
				summaryThis += elementSingle.getText()+"<br/>";
			}
			
			throw new CheckException("This test data should pass the Eligibility Check!("+summaryThis+")");
		}
		
		//element = e2EUtil.fluentWait(By.id("s2irefTransactionID"));
		summary += "Pass the Eligibility Check."+"<br/>";

		return summary;
	}

}
