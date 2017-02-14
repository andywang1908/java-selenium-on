package ca.on.gov.gsc.s2i.e2e.acscp2;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ca.on.gov.gsc.s2i.e2e.util.E2EUtil;

//wait
//test case
//run in official
//time out
public abstract class ACSCP2Base {
	protected static WebDriver driver;
	protected static E2EUtil e2EUtil;
	protected static String jsonFile;
	protected static JSONObject jsonObject;

    //10.160.200.146  10.160.193.226   10.160.200.209
	//http://10.160.200.146:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://10.160.193.226:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://10.160.193.226:10039/sodp/portal/s2i
	//http://10.160.200.209:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://localhost:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P1Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q
	//https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P2Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q
	//2222PBT  2222PDT
	protected static String rootUrl = "https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P2Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q";
	protected int mode=1;//0 debug 1 loop run 2 single

	protected abstract void runCase(JSONObject jsonObject) throws Exception;

	@Test
	public void testCaseArray() throws Exception {
		System.out.println("fda11");

		JSONArray caseArray = jsonObject.getJSONArray("caseArray");

		String field,value;

		for (int i = 0; i < caseArray.length(); i++)
		{
		    this.runCase(caseArray.getJSONObject(i));

			if ( mode==0 ) break;

			field = "skip";
			value = e2EUtil.jsonString(jsonObject, field);
			if ( "true".equals(value) ) {
			} else {
				this.restartRoot();
			}
		}
	}

	@Before
	public void setUp() throws Exception {
		if ( jsonObject==null ) {
			String url = this.getClass().getResource(jsonFile+".json").getPath();
			BufferedReader br = new BufferedReader(new FileReader(url));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append("\r\n");
			        line = br.readLine();
			    }
			    String everything = sb.toString();
				jsonObject = new JSONObject(everything);
			} finally {
			    br.close();
			}
		}

		if ( driver==null ) {
			//{pageInfo:{pageName:'pageLogin'},posts:[{post_id:'1001'},{post_id:'1002'}]}

	    	File file;
	    	//TODO config
	    	file = new File("C:/green/chromedriver.exe");
	    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
	    	//file = new File("C:/green/IEDriverServer.exe");
	    	//System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

	    	//driver = new FirefoxDriver();
	    	driver = new ChromeDriver();
	        //driver = new InternetExplorerDriver();

	    	driver.manage().window().setPosition(new Point(0,0));
	    	driver.manage().window().setSize(new Dimension(1024,900));

			if ( e2EUtil==null ) {
				e2EUtil = E2EUtil.getInstance(driver);
			}

	    	//driver = new HtmlUnitDriver(true);
	        //driver = new InternetExplorerDriver();

	        // And now use this to visit Google
	        //driver.get("http://www.google.com");
	        this.restartRoot();
		}


        /*
		boolean flag = true;
		while (flag) {
			try {
				WebElement el = driver.findElement(By.name("q"));
				if (el != null) // Check if the required date element is found
								// or not
				{
					System.out.println("found...");
					flag = false;
				}
			} catch (Exception e) { // Catches exception if no element found
				try {
					System.out.println("wait...");
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}*/
	}

	protected void checkIt() throws InterruptedException {
		if ( this.mode>=0 ) {
			Thread.sleep(1000);
		}
	}

	protected void waitIt() throws InterruptedException {
		if ( this.mode>=0 ) {
			Thread.sleep(1000);
		}
	}

	protected final void restartRoot() throws Exception {
		Thread.sleep(1000);//check result;

        driver.get(rootUrl);
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        e2EUtil.jsAlert();

		//List<WebElement> elementList;
		WebElement element;

        this.waitIt();
        element = driver.findElement(By.className("s2iNavigateAway"));
        assertNotNull(element);

        element.click();
	}

	//application form
	protected final void fillApplicationParent(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		//String field;
		//String value;
        this.waitIt();

		//this.js("var hj0=\"#acscp1_appMailingAddrNote\";var hj=\" <input type=button value=a onclick='var vv=$(\"+hj0+\").val();alert(vv);'> \";$('#acscp1_applicant_FN').after(hj)");

		String mockJson = "{\"description\":\"175 sh 185 sh, north york, ON M2J1K1\",\"country\":\"CANADA\",\"canadaAddress\":{\"line1\":\"175 sh\",\"line2\":\"185 sh\",\"municipalityName\":\"north york\",\"provinceCode\":\"ON\",\"postalCode\":\"M2J1K1\",\"recordType\":97,\"country\":\"CANADA\"}}";
		e2EUtil.js("var mockMail='"+mockJson+"';$('input[name=\"applicant.mailingAddressJSON\"]').val(mockMail);$('input[name=\"employer.mailingAddressJSON\"]').val(mockMail);$('input[name=\"otherParent.mailingAddressJSON\"]').val(mockMail);");

		//form1 personal information
		e2EUtil.jsInput("applicant.firstName","Andy");
		e2EUtil.jsInput("applicant.lastName","Wang");
		//field = "agreeDate";
		e2EUtil.jsDate("applicant.DOB", e2EUtil.formatDate("1975-10-11"));
		e2EUtil.jsInput("applicant.SIN","000000018");
		//field = "childPer";
		e2EUtil.jsRadio("applicant.preferedLanguage", "en");
		e2EUtil.jsRadio("receiveUCCB", "false");

		//form2
		e2EUtil.jsInput("employer.companyName","IBM");
		e2EUtil.jsInput("employer.contactName","Andy");
		e2EUtil.jsInput("employer.phone","6474787713");

        //if ( 1==1 ) return;
		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	protected final void fillConditionType(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		String field;
        this.waitIt();

		//elementList = driver.findElements(By.name("isRecipient"));
		//assertEquals("check option", elementList.size(), 2);
		//element = elementList.get(1);
		//element.click();
		field = "proofIncome";
		e2EUtil.jsRadio(field, "true");
		field = "optOut";
		e2EUtil.jsRadio(field, "true");
		field = "complexIncome";
		e2EUtil.jsRadio(field, "false");
		field = "taxIsFiled";
		e2EUtil.jsRadio(field, "true");

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();

	}

	protected final void fillFileNo(JSONObject jsonObject) throws InterruptedException {
		WebElement element;
		String field;
        this.waitIt();

		//elementList = driver.findElements(By.name("isRecipient"));
		//assertEquals("check option", elementList.size(), 2);
		//element = elementList.get(1);
		//element.click();
		field = "refNum";
		e2EUtil.jsInput(field, e2EUtil.jsonString(jsonObject, field));

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	//waiver
	protected final void fillUpload(JSONObject jsonObject) throws InterruptedException {
		List<WebElement> elementList;
		WebElement element;
		String field;
		String value;
		//String attr;
        this.waitIt();

		field = "file1";
		value = e2EUtil.jsonString(jsonObject, field);
        //not test file upload here
        if ( value==null ) {
        	e2EUtil.js("$('#acscp1_SkipFileUpload').click();");
        } else {
            //cat0File0
            //datafile12 datafile11
            elementList = driver.findElements(By.name("cat0File0"));
            if ( elementList.size()>=1 ) {
            	element = elementList.get(0);
            	String fileName = "C:/Users/WangAn1/Downloads/example.pdf";

        		//http://stackoverflow.com/questions/11256732/how-to-handle-windows-file-upload-using-selenium-webdriver
        		element.sendKeys(fileName);
            }
            elementList = driver.findElements(By.name("uploadCheckBox"));
            if ( elementList.size()>=1 ) {
            	element = elementList.get(0);
            	element.click();
            }

            //jQuery is not enough
            //this.js("$('#uploadButton').click();");
            elementList = driver.findElements(By.id("uploadButton"));
            if ( elementList.size()>=1 ) {
            	element = elementList.get(0);
            	element.click();
            }

            //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(10*1000);
        }
	}
}
