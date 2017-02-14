package ca.on.gov.gsc.s2i.e2e.iac.scenario;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.iac.SeleniumIAC;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;

public class SameAddressTest extends SeleniumIAC {
	public SameAddressTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		mode = 0;
		//resultId = "Same Address";
		jsonFile = "Same Address";
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		//String key;
		String value;

		String summary = "";
		String cardNo = "";

		cardNo = this.fillHealthCard(jsonObject);

		this.fillPostCode(jsonObject.getJSONObject("postCodeResidential"),1);
		summary = this.fillPostCodeCheck("residentialAddressJson");

		elementList = driver.findElements(By.name("sameAddress"));
		if ( elementList.size()>0 ) {
			element = elementList.get(0);
			element.click();
			value = element.getAttribute("checked");
			if ( "true".equals(value) ) {
			} else {
				//String mockJson = "{\"description\":\"175 sh 185 sh, north york, ON M2J1K1\",\"country\":\"CANADA\",\"canadaAddress\":{\"line1\":\"175 sh\",\"line2\":\"185 sh\",\"municipalityName\":\"north york\",\"provinceCode\":\"ON\",\"postalCode\":\"M2J1K1\",\"recordType\":97,\"country\":\"CANADA\"}}";
				//e2EUtil.js("var mockMail='"+mockJson+"';$('input[name=\"mailingAddressJson\"]').val(mockMail);");
				throw new Exception("This address can not be used as mailing address!");
			}
		}
		elementList = driver.findElements(By.className("s2iNavigateAway"));
		for ( WebElement elementSingle: elementList) {
			if ( "Review".equals(elementSingle.getText()) ) {
				elementSingle.click();
				break;
			}
		}

		
		this.fillContact(jsonObject);

		summary = this.fillSummaryCheck(cardNo);

		return summary;
	}

	private void resetPage() throws Exception {
		super.restartRoot();
	}
}
