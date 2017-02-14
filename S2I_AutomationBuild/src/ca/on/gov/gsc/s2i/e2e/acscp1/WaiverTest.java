package ca.on.gov.gsc.s2i.e2e.acscp1;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WaiverTest extends ACSCP1Base {
	@Override
	@Before
	public void setUp() throws Exception {
		//mode = 0;
		//rootUrl = "";
		jsonFile = "WaiverTest";
		super.setUp();
	}

	@Override
	protected void runCase(JSONObject jsonObject) throws Exception {
		//List<WebElement> elementList;
		WebElement element;
		String field;
		String value;
        //element = driver.findElement(By.name("q"));

		field = "skip";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( "true".equals(value) ) {
			return;
		}

		String desc = "waiver test start";
		field = "desc";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value!=null ) {
			desc += ":"+value;
		}
		e2EUtil.log(desc);

        this.fillConditionType(jsonObject);
        this.fillConditionChild(jsonObject);
        this.fillConditionOther(jsonObject);
        this.fillApplicationParent(jsonObject);
        this.fillApplicationChild(jsonObject);
        this.fillApplicationExpense(jsonObject);
        this.fillWaiver(jsonObject);
        this.fillUpload(jsonObject);

        //if ( 1==1 ) return;

		//check the result
		//elementList = driver.findElements(By.name("applicant.firstName"));
		//assertEquals("check condition result", elementList.size(), 1);
        element = driver.findElement(By.id("acsc_totalFee"));
		assertEquals("check fee result","$0",element.getText());
	}
}
