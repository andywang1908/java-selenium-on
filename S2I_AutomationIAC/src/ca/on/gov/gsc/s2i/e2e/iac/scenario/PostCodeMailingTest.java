package ca.on.gov.gsc.s2i.e2e.iac.scenario;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.iac.SeleniumIAC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;

public class PostCodeMailingTest extends SeleniumIAC {
	public PostCodeMailingTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//mode = 0;
		//jsonFile = "Postal Code Check";
		jsonFile = "Postal Code Mailing";
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		//List<WebElement> elementList;
		WebElement element;
		//String key,value;

		String summary = "";

		this.fillHealthCard(jsonObject);

		this.fillPostCode(jsonObject.getJSONObject("postCodeResidential"),2);
		summary = this.fillPostCodeCheck("mailingAddressJson");

		return summary;
	}

	private void resetPage() throws InterruptedException {
		e2EUtil.js("history.go(0);");
		e2EUtil.jsAlert();
	}
}
