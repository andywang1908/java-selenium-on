package ca.on.gov.gsc.s2i.e2e.acscp1;

import static org.junit.Assert.*;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConditionTest extends ACSCP1Base {
	@Override
	@Before
	public void setUp() throws Exception {
		//mode = 0;
		//rootUrl = "";
		jsonFile = "ConditionTest";
		super.setUp();
	}

	@Override
	protected void runCase(JSONObject jsonObject) throws Exception {

		List<WebElement> elementList;
		//WebElement element;
		String field;
		String value;
        //element = driver.findElement(By.name("q"));

		field = "skip";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( "true".equals(value) ) {
			return;
		}

		String desc = "conditon test start";
		field = "desc";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value!=null ) {
			desc += ":"+value;
		}
		e2EUtil.log(desc);

        this.fillConditionType(jsonObject);
        this.fillConditionChild(jsonObject);
        this.fillConditionOther(jsonObject);

		//check the result
        Thread.sleep(1000);
		elementList = driver.findElements(By.name("applicant.firstName"));
		assertEquals("check condition result", elementList.size(), 1);
	}
}

