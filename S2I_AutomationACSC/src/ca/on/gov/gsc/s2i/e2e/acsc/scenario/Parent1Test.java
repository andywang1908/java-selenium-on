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

public class Parent1Test extends SeleniumACSC {
	public Parent1Test(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/*keep close  02 15 true-c-43*/
    @Parameters
    public static Collection<String[]> addParamter() {
    	return Arrays.asList(new String[][] { { "ACSC-P1-0001", "keep" } });
    }

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);
		
		mode = 0;
		//resultId = "Same Address";
		jsonFile = "Parent1";
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

}
