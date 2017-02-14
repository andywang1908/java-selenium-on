package ca.on.gov.gsc.s2i.e2e.bf.scenario;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.bf.SeleniumBF;
import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;

public class ConflictTest extends SeleniumBF {
	public ConflictTest(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent) ;
	}
	/*keep close 
    @Parameters
    public static Collection<String[]> addParamter() {
        return Arrays.asList(new String[][] { { "TC0357", "keep" } });
    }*/

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		super.globalConfig(automationConfig);

		//mode = 0;
		//resultId = "Same Address";
		jsonFile = "Test Set Negative";//Conflict Negative Test Set Negative
	}

	@Override
	protected String runCase(JSONObject jsonObject) throws Exception {
		//List<WebElement> elementList;
		WebElement element;
		//String key,value;

		String summary = "";

		this.fillPostCode(jsonObject,1);
		
		String expectList = jsonObject.getString("q16");
		String[] expectArray = expectList.split(",");
		
		summary = "";
		for (int i=0;i<expectArray.length;i++) {
			summary += this.fillPostCodeCheck(expectArray[i].trim(), false);
		}
		System.out.println(summary);

		return summary;
	}

	private void resetPage() throws InterruptedException {
		e2EUtil.js("history.go(0);");
		e2EUtil.jsAlert();
	}
}
