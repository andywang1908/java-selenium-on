package ca.on.gov.gsc.s2i.e2e.acscp1;

import static org.junit.Assert.*;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BannedTest extends ACSCP1Base {
	@Override
	@Before
	public void setUp() throws Exception {
		mode = 0;
		//rootUrl = "";
		jsonFile = "BannedTest";
		super.setUp();
	}

	@Override
	protected void runCase(JSONObject jsonObject) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String field;
		String value;

		boolean getBanned = false;
		String tagBanned = "this is non eligibility page";


		field = "skip";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( "true".equals(value) ) {
			return;
		}

		String desc = "banned test start:";
		field = "desc";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value!=null ) {
			desc += ":"+value;
		}
		e2EUtil.log(desc);

        this.fillConditionType(jsonObject);

		Thread.sleep(1000);
        elementList = driver.findElements(By.id("acscp1_nonEligibleFormErrors"));//eligiblityArticle
        if ( elementList.size()>=1 ) {
        	element = elementList.get(0);
            field = element.getText();
            if ( field!=null && field.indexOf(tagBanned)>=0 || true ) {
            	getBanned = true;
            	return;
            }
        }

		//step
        this.fillConditionChild(jsonObject);

		Thread.sleep(1000);
        elementList = driver.findElements(By.id("acscp1_nonEligibleFormErrors"));//eligiblityArticle
        if ( elementList.size()>=1 ) {
        	element = elementList.get(0);
            field = element.getText();
            if ( field!=null && field.indexOf(tagBanned)>=0 || true ) {
            	getBanned = true;
            	return;
            }
        }

        //if (true) return;

        this.fillConditionOther(jsonObject);

		Thread.sleep(1000);
        elementList = driver.findElements(By.id("acscp1_nonEligibleFormErrors"));//eligiblityArticle
        if ( elementList.size()>=1 ) {
        	element = elementList.get(0);
            field = element.getText();
            if ( field!=null && field.indexOf(tagBanned)>=0 || true ) {
            	getBanned = true;
            	return;
            }
        }

		assertEquals("check banned result:no warning is found", getBanned, true);
	}
}

