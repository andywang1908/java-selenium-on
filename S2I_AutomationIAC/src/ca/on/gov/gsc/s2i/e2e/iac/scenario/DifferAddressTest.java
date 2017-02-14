package ca.on.gov.gsc.s2i.e2e.iac.scenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.iac.SeleniumIAC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;

public class DifferAddressTest extends SeleniumIAC {
	public DifferAddressTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}
	/**/
    @Parameters
    public static Collection<String[]> addParamter() {
        return Arrays.asList(new String[][] { { "IAC-0591", "keep" } });
    }

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//mode = 0;
		jsonFile = "Different Address";
		resultId = "Differ Address";

		jsonFile = "UAT only MOH";
		resultId = "UAT only MOH";
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		//WebElement element;
		//String key,value;

		String summary = "";
		String cardNo = "";

		cardNo = this.fillHealthCard(jsonObject);

		this.fillPostCode(jsonObject.getJSONObject("postCodeResidential"),1);
		summary += this.fillPostCodeCheck("residentialAddressJson");

		this.fillPostCode(jsonObject.getJSONObject("postCodeMailing"),2);
		summary += this.fillPostCodeCheck("mailingAddressJson");//result summary fail void
		
		elementList = driver.findElements(By.className("s2iNavigateAway"));
		for ( WebElement elementSingle: elementList) {
			if ( "Review".equals(elementSingle.getText()) ) {
				elementSingle.click();
				break;
			}
		}
		
		this.fillContact(jsonObject);

		summary += this.fillSummaryCheck(cardNo);

		return summary;
	}
}
