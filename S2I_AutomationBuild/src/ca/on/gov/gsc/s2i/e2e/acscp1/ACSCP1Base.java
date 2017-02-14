package ca.on.gov.gsc.s2i.e2e.acscp1;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.model.E2ECaseResult;
import ca.on.gov.gsc.s2i.e2e.model.E2ELoopResult;
import ca.on.gov.gsc.s2i.e2e.util.E2EUtil;

//wait
//test case
//run in official
//time out
public abstract class ACSCP1Base {
	protected static WebDriver driver;
	protected static E2EUtil e2EUtil;
	protected static String jsonFile;
	protected static JSONObject jsonCase;

    //10.160.200.146  10.160.193.226   10.160.200.209
	//http://10.160.200.146:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://10.160.193.226:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://10.160.193.226:10039/sodp/portal/s2i
	//http://10.160.200.209:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//http://localhost:10039/sodp/poc/s2i?uri=s2i:ACSC000P1Info
	//https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P1Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q
	//https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P2Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q
	//2222PBT  2222PDT
	protected static String rootUrl = "https://wwwdev.mobile.services.ebc.gov.on.ca/sodp/poc/s2i?uri=s2i:ACSC000P1Info&lang=en&productGroup=acscp1&s2iByPassCode=3hQpqYQ1HP7XG6Q";
	protected int mode=1;//0 debug single 1 loop run

	protected abstract void runCase(JSONObject jsonObject) throws Exception;

	@Test
	public void testCaseArray() throws Exception {
		Gson gson = new Gson();

		JSONArray caseArray = jsonCase.getJSONArray("caseArray");
		E2ECaseResult caseResult = null;
		List<E2ELoopResult> loopResultList;
		String fileId = e2EUtil.jsonString(jsonCase, "id");
		String filePath = "c:/green/result/"+fileId+".res";
		try {
			String fileContent = null;
			fileContent = this.readFile(filePath);

			caseResult = gson.fromJson(fileContent, E2ECaseResult.class);
		} catch ( Exception e ) {
			caseResult = null;
		}
		if ( caseResult==null ) {
			caseResult = new E2ECaseResult();
			caseResult.setDesc( e2EUtil.jsonString(jsonCase, "desc") );
			caseResult.setId( e2EUtil.jsonString(jsonCase, "id") );
		}

		loopResultList = caseResult.getLoopResultList();
		if ( loopResultList==null ) {
			loopResultList = new ArrayList<E2ELoopResult>();
			caseResult.setLoopResultList(loopResultList);
		}

		String field,value;

		JSONObject jsonRow;
		E2ELoopResult loopResult;
		for (int i = 0; i < caseArray.length(); i++)
		{
			jsonRow = caseArray.getJSONObject(i);

			loopResult = null;
			String rowId = e2EUtil.jsonString(jsonRow, "id");
			if ( rowId==null ) {
				rowId = "";
			}

			for (E2ELoopResult loopResultSingle: loopResultList) {
				if ( rowId.equals( loopResultSingle.getId() ) ) {
					loopResult = loopResultSingle;
				}
			}
			if ( loopResult==null ) {
				loopResult = new E2ELoopResult();
				loopResultList.add(loopResult);
			}

			loopResult.setId( e2EUtil.jsonString(jsonRow, "id") );
			loopResult.setDesc( e2EUtil.jsonString(jsonRow, "desc") );

			if ( "success".equals(loopResult.getResult()) && mode==1 ) {
				continue;
			}

			field = "skip";
			value = e2EUtil.jsonString(jsonCase, field);
			if ( "true".equals(value) ) {
				loopResult.setResult("success");
				continue;
			}

			try {
				this.runCase(jsonRow);
				loopResult.setResult("success");
			} catch ( Exception e ) {
				loopResult.setResult("error:"+e.getMessage());
			}

			if ( mode==0 ) {
				break;
			} else {
				this.restartRoot();
			}
		}

		String result = gson.toJson(caseResult);
		System.out.println(result);
		this.writeFile(filePath, result);
	}

	@AfterClass
	public static void tearDownClass() {
		e2EUtil.log("tearDownClass()");
        if ( driver!=null ) {
        	//for check result should sleep 3 seconds first
        	//driver.quit();//driver.close();
        }
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		Runtime.getRuntime().exec("c:/green/e2e/clean.bat");// cmd /c start C:/green/clean.bat
		Thread.sleep(1000);
	}

	private void writeFile(String filePath,String fileContent) throws Exception {
		PrintWriter out = new PrintWriter(filePath);

		out.print(fileContent);

		out.close();
	}

	private String readFile(String filePath) throws Exception {
		String result = null;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\r\n");
		        line = br.readLine();
		    }
		    result = sb.toString();
		} finally {
	    	if ( br!=null ) {
				br.close();
	    	}
		}

		return result;
	}

	//
	@Before
	public void setUp() throws Exception {
		if ( jsonCase==null ) {
			String url = this.getClass().getResource(jsonFile+".json").getPath();
		    jsonCase = new JSONObject(this.readFile(url));
		}

		if ( driver==null ) {
			//{pageInfo:{pageName:'pageLogin'},posts:[{post_id:'1001'},{post_id:'1002'}]}

	    	File file;
	    	//TODO config
	    	file = new File("c:/green/e2e/chromedriver.exe");
	    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
	    	//file = new File("C:/green/IEDriverServer.exe");
	    	//System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

	    	//driver = new FirefoxDriver();
	    	//ChromeOptions options = new ChromeOptions();//chrome.detach
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
		List<WebElement> elementList;

		//for ie https
        this.waitIt();
        //elementList = driver.findElements(By.name("actionIcon"));
        //e2EUtil.js("document.getElementById('overridelink').click()");
		//Thread.sleep(10000);//check result;


        this.waitIt();
        element = driver.findElement(By.className("s2iNavigateAway"));
        assertNotNull(element);

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
		field = "isRecipient";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));
		field = "docType";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));
		field = "incAmt";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	protected final void fillConditionChild(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		String field;
        this.waitIt();

		field = "complexIncome";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));
		/*
		Thread.sleep(500);
		field = "complexIncome";
		this.jsRadio(field, this.jsonString(jsonObject, field));*/
		field = "liveInOntario";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));
		field = "childInAge";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));
		field = "childPer";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	protected final void fillConditionOther(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		String field;
		String value;
        this.waitIt();

		//element.sendKeys("02/01/2013");
		field = "agreeDate";//Doc B
		e2EUtil.jsDate(field, e2EUtil.jsonDate(jsonObject, field));
		field = "agreeDate";//Doc A
		e2EUtil.jsDate("supord.date", e2EUtil.jsonDate(jsonObject, field));

		field = "province";
		//TODO function
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value==null || "".equals(value) ) {
			e2EUtil.js("$('select[name=\"supord.province\"]').val('ON');");
		} else {
			e2EUtil.js("$('select[name=\"supord.province\"]').val('"+value+"');");
		}

		field = "proofIncome";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value==null || "".equals(value) ) {
		} else {
			e2EUtil.js("$('input[name=\""+field+"\"][value="+value+"]').click();");
		}

		field = "incomeIsSeasonal";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( value==null || "".equals(value) ) {
		} else {
			e2EUtil.js("$('input[name=\""+field+"\"][value="+value+"]').click();");
		}

		field = "taxIsFiled";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
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
		e2EUtil.jsDate("applicant.DOB", e2EUtil.formatDate("1975-10-10"));
		e2EUtil.jsInput("applicant.SIN","000000018");
		//field = "childPer";
		e2EUtil.jsRadio("applicant.preferedLanguage", "en");

		//form3
		e2EUtil.jsInput("otherParent.firstName","Mary");
		e2EUtil.jsInput("otherParent.lastName","Wan");
		e2EUtil.jsRadio("otherParent.preferedLanguage", "en");

		//form4 support arrangement
		e2EUtil.jsInput("froNumber","123456");
		e2EUtil.jsRadio("supportPayPeriod", "WK");
		e2EUtil.jsInput("agreeFileNo","A20896");
		e2EUtil.jsRadio("beforeCourt", "false");
		e2EUtil.jsRadio("beforeCourt", "false");

		//form5 other financial support
		e2EUtil.jsRadio("receiveUCCB", "true");
		e2EUtil.jsInput("uccbAmount","800");
		e2EUtil.jsRadio("acscp1_spousalPayerNo", "false");
		e2EUtil.jsRadio("isSpousalPayer", "false");
		e2EUtil.jsRadio("isSpousalReceiver", "false");

        //if ( 1==1 ) return;
		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	protected final void fillApplicationChild(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		//String field;
		//String value;
        this.waitIt();

		e2EUtil.jsInput("childs[0].firstName","Yun");
		e2EUtil.jsInput("childs[0].lastName","Wang");
		e2EUtil.jsDate("childs[0].DOB","2008-12-12");

		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	//expense
	protected final void fillApplicationExpense(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		String field;
		String value;
        this.waitIt();

		field = "childExpenseAddSE0";//
		value = e2EUtil.jsonString(jsonObject, field);
		e2EUtil.jsRadio("childExpense[0].addSE", "false");//value

		e2EUtil.jsInput("childExpense[0].expense['NET'][0].description","Current child support document");
		e2EUtil.jsInput("childExpense[0].expense['NET'][0].amount","900.0");
		e2EUtil.jsSelect("childExpense[0].expense['NET'][0].status","N");
		e2EUtil.jsInput("childExpense[0].expense['MD'][0].description","Medical and dental insurance");
		e2EUtil.jsInput("childExpense[0].expense['MD'][0].amount","901.0");
		e2EUtil.jsSelect("childExpense[0].expense['MD'][0].status","N");
		e2EUtil.jsInput("childExpense[0].expense['HEALTH'][0].description","Health-related expenses");
		e2EUtil.jsInput("childExpense[0].expense['HEALTH'][0].amount","902.0");
		e2EUtil.jsSelect("childExpense[0].expense['HEALTH'][0].status","N");
		e2EUtil.jsInput("childExpense[0].expense['POST'][0].description","Education expenses");
		e2EUtil.jsInput("childExpense[0].expense['POST'][0].amount","903.0");
		e2EUtil.jsSelect("childExpense[0].expense['POST'][0].status","N");
		e2EUtil.jsInput("childExpense[0].expense['EXT'][0].description","Extracurricular activities");
		e2EUtil.jsInput("childExpense[0].expense['EXT'][0].amount","904.0");
		e2EUtil.jsSelect("childExpense[0].expense['EXT'][0].status","N");

		//elementList = driver.findElements(By.name(field));
		//if ( elementList.size()>0 ) {}
		e2EUtil.jsRadio("childExpense[1].addSE", "false");


        //if ( 1==1 ) return;
		this.checkIt();
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
	}

	//waiver
	protected final void fillWaiver(JSONObject jsonObject) throws InterruptedException {
		//List<WebElement> elementList;
		WebElement element;
		String field;
		String value;
		//String attr;
        this.waitIt();

		field = "skipWaiver";
		value = e2EUtil.jsonString(jsonObject, field);
		if ( "true".equals(value) ) {
			e2EUtil.js("$('#acscp1_SkipFeeWaiver').click();");
			return;
		}

		field = "primary";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		field = "liquid";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		field = "networth";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		field = "numOfInHouse";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

		field = "grossRange";
		e2EUtil.jsRadio(field, e2EUtil.jsonString(jsonObject, field));

        if ( true ) return;
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
