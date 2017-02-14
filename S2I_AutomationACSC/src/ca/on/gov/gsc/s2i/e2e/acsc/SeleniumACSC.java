package ca.on.gov.gsc.s2i.e2e.acsc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.tool.SeleniumBase;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;
import ca.on.gov.gsc.s2i.e2e.util.E2EUtil;
import ca.on.gov.gsc.s2i.e2e.util.WebDriverUtil;

//wait
//test case
//run in official
//time out
public abstract class SeleniumACSC extends SeleniumBase {
	public SeleniumACSC(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		automationConfig.setDriverType( PropertyACSC.getInstance().getProperty("s2i.acsc.driverType") );
		automationConfig.setLocalDic( PropertyACSC.getInstance().getProperty("s2i.acsc.localDic") );
		automationConfig.setRootUrl( PropertyACSC.getInstance().getProperty("s2i.acsc.rootUrl") );
		
		automationConfig.setResultDic( PropertyACSC.getInstance().getProperty("s2i.acsc.resultDic") );
		automationConfig.setTestDataDic( PropertyACSC.getInstance().getProperty("s2i.acsc.testDataDic") );

		automationConfig.setSkipPositive( PropertyACSC.getInstance().getProperty("s2i.acsc.skipPositive") );
		automationConfig.setSkipNegative( PropertyACSC.getInstance().getProperty("s2i.acsc.skipNegative") );
		
		automationConfig.setWindowWidth( 1024 );
		automationConfig.setWindowHeight( 900 );
		
		automationConfig.setNameSpace( PropertyACSC.getInstance().getProperty("s2i.acsc.nameSpace") );

		//return automationConfig;
	}

	/*
	@After
	public void tearDown() throws Exception {
	    super.tearDown();
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}*/

	@Override
	protected final void restartRoot() throws Exception {
		Thread.sleep(500);//check result;
		
		WebDriverUtil webDriverUtil = WebDriverUtil.getInstance();
		
		AutomationConfig config = webDriverUtil.getAutomationConfig();

		//TODO put to other place
        driver.get( config.getRootUrl() );
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        e2EUtil.jsAlert();

		//List<WebElement> elementList;
		WebElement element = null;
		//List<WebElement> elementList;

		//for ie https
        //this.waitIt();
        //elementList = driver.findElements(By.name("actionIcon"));
        //e2EUtil.js("document.getElementById('overridelink').click()");
		//Thread.sleep(10000);//check result;

        //this.waitIt();
		/* only for develop local vm*/
        element = driver.findElement(By.className("s2iNavigateAway"));

        if ( element!=null ) {
        	//TODO handle not in screen or shown
    		try {
    			element.click();
    		} catch ( Exception e ) {
    		}
            //element.click();
        }
	}

	protected void fillEligibility(JSONObject jsonObject) throws InterruptedException {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		//first page can not check page because of restart
		//this.checkPageError();
		
		try {
			jsonModel = jsonObject.getJSONObject("eligibility");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		e2EUtil.fluentWait(By.name("isRecipient"));
		
		if ( !gotIt ) {
		} else {
			key = "isRecipient";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "docType";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "incAmt";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			this.waitIt();

			elementList = driver.findElements(By.name("complexIncome"));
			if ( elementList.size()==0 ) {
				return;
			}

			this.waitIt();
			key = "complexIncome";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "liveInOntario";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "childInAge";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "childPer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			this.waitIt();

			elementList = driver.findElements(By.name("agreeDate"));
			if ( elementList.size()==0 ) {
				return;
			}

			this.waitIt();
			//element.sendKeys("02/01/2013");
			key = "agreeDate";//Doc B
			e2EUtil.jsDate(key, e2EUtil.jsonDate(jsonModel, key));
			key = "agreeDate";//Doc A
			e2EUtil.jsDate("supord.date", e2EUtil.jsonDate(jsonModel, key));
			key = "agreeDate";//Doc E  can not use "2010-09-19" ,but "2010/09/19" ok, do not know why
			e2EUtil.jsDate("norcDate", "2010/09/19");//e2EUtil.jsonDate(jsonModel, key)

			key = "province";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsSelect(key, value);
			key = "province";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsSelect("supord.province", value);
			
			key = "taxIsFiled";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "proofIncome";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "incomeIsSeasonal";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			
			elementList = driver.findElements(By.className("s2iNavigateAway"));
			if ( elementList.size()>0 ) {
				element = elementList.get(0);
				element.click();
			}
		}

		//this.waitIt();
		//element = e2EUtil.fluentWait(By.className("s2iNavigateAway"));
	}

	protected void fillApplicationParent(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		
		this.checkPageError();
		
		try {
			jsonModel = jsonObject.getJSONObject("parent");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		//e2EUtil.fluentWait(By.name("isRecipient"));

		String mockJson = "{\"description\":\"175 sh 185 sh, north york, ON M2J1K1\",\"country\":\"CANADA\",\"canadaAddress\":{\"line1\":\"175 sh\",\"line2\":\"185 sh\",\"municipalityName\":\"north york\",\"provinceCode\":\"ON\",\"postalCode\":\"M2J1K1\",\"recordType\":97,\"country\":\"CANADA\"}}";
		e2EUtil.js("var mockMail='"+mockJson+"';$('input[name=\"applicant.mailingAddressJSON\"]').val(mockMail);$('input[name=\"employer.mailingAddressJSON\"]').val(mockMail);$('input[name=\"otherParent.mailingAddressJSON\"]').val(mockMail);");

		if ( !gotIt ) {
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
			//type other
			e2EUtil.jsInput("supord.fileNumber","A20896");
			e2EUtil.jsInput("norcID","A20896");//sometimes
			e2EUtil.jsInput("supord.justice","Tom");
			e2EUtil.jsInput("supord.city","Toronto");
			
			e2EUtil.jsRadio("beforeCourt", "false");

			//form5 other financial support
			e2EUtil.jsRadio("receiveUCCB", "true");
			e2EUtil.jsInput("uccbAmount","800");
			e2EUtil.jsRadio("acscp1_spousalPayerNo", "false");
			e2EUtil.jsRadio("isSpousalPayer", "false");
			e2EUtil.jsRadio("isSpousalReceiver", "false");
		} else {
			key = "firstName";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.firstName", value);
			key = "lastName";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.lastName", value);
			key = "dob";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsDate("applicant.DOB", e2EUtil.formatDate(value));
			key = "sin";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.SIN", value);
			key = "preferedLanguage";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio("applicant.preferedLanguage", value);
			key = "middleName";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.middleName", value);
			key = "phone";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.telephone", value);
			key = "email";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("applicant.email", value);
			

			key = "nameEmployer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("employer.companyName", value);
			key = "contactNameEmployer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("employer.contactName", value);
			key = "contactPhoneEmployer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("employer.phone", value);

			//form3
			key = "firstNameOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("otherParent.firstName", value);
			key = "lastNameOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("otherParent.lastName", value);
			key = "preferedLanguageOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio("otherParent.preferedLanguage", value);
			key = "middleNameOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("otherParent.middleName", value);
			key = "phoneOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("otherParent.telephone", value);
			key = "emailOther";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("otherParent.email", value);
			
			//form4 support arrangement
			/**/
			key = "froNumber";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput(key, value);
			key = "supportPayPeriod";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "agreeFileNo";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput(key, value);
			//for other doc type
			key = "fileNumber";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("supord.fileNumber", value);
			key = "norcID";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput(key, value);//sometimes
			if ( value!=null && !value.equals("") ) {
				e2EUtil.jsInput("agreeFileNo", value);//make test data easy
			}
			key = "justice";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("supord.justice", value);
			key = "city";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("supord.city", value);

			key = "supordLevel";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsSelect("supord.level", value.toUpperCase());

			key = "beforeCourt";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);

			//form5 other financial support
			key = "receiveUCCB";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "uccbAmount";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput(key, value);
			/*
			key = "spousalPayerNo";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio("acscp1_spousalPayerNo", value);*/
			key = "isSpousalPayer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			this.waitIt();//twice to make it checked , why?
			key = "isSpousalPayer";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			
			key = "spousalPayerAmount";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("payerAmount", value);

			key = "isSpousalReceiver";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "spousalReceiverAmount";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsInput("receiverAmount", value);
		}
				
		//browser.focus("css=a.indexTwitter");
		/*
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File("c:/tmp/screenshot.png"));*/

		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
		
		/*court level  may click two times
		try {
			Thread.sleep(100);
			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			System.out.println("double click");
		} catch (Exception e) {
			
		}*/
	}

	protected void fillApplicationChild(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		JSONArray childList = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		
		//temp remove since application page has double click
		//this.checkPageError();
		
		try {
			childList = jsonObject.getJSONArray("childList");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		e2EUtil.fluentWait(By.className("s2iNavigateAway"));
		
		if ( !gotIt ) {
			e2EUtil.jsInput("childs[0].firstName","Yun");
			e2EUtil.jsInput("childs[0].lastName","Wang");
			e2EUtil.jsDate("childs[0].DOB","2008-12-12");
		} else {
			for(int i=0;i<childList.length();i++) {
				jsonModel = childList.getJSONObject(i);

				key = "firstName";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsInput("childs["+i+"].firstName",value);
				key = "middleName";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsInput("childs["+i+"].middleName",value);
				key = "lastName";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsInput("childs["+i+"].lastName",value);
				key = "birthday";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsDate("childs["+i+"].DOB",value);
			}
		}
		
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
		//this.waitIt();
		/*
		try {
			element.click();
		} catch ( Exception e ) {
		}*/
		this.waitIt();
	}

	protected void fillApplicationExpense(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		JSONArray childList = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		
		this.checkPageError();
		
		try {
			//jsonModel = jsonObject.getJSONObject("expense");
			childList = jsonObject.getJSONArray("childList");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		//e2EUtil.fluentWait(By.name("isRecipient"));
		
		if ( !gotIt ) {
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
		} else {
			String feeValue = "";
			//String feeInput = "";
			String[] feeInput = {"NET","MD","HEALTH","POST","EXT"};
			String[] newFeeInput = {"PARENT","MED","ORTHO"};
			String[] feeArray;
			for(int i=0;i<childList.length();i++) {
				jsonModel = childList.getJSONObject(i);

				//old fee
				for(int j=0;j<5;j++) {
					key = "fee"+(j+1);
					feeValue = e2EUtil.jsonString(jsonModel, key);
					if ( feeValue==null || feeValue.equals("") ) {
						continue;
					}
					if ( feeValue.indexOf("&&")>0 ) {
						feeValue = feeValue.substring(0, feeValue.indexOf("&&"));
					}
					feeArray = feeValue.split("&");
					if ( feeArray.length>=3 ) {
						/**/
						e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].description",feeArray[0]);
						value = "";
						if ( feeArray[1].equals(feeArray[2]) ) {
							value = "N";//no change
						} else if ( feeArray[2].equals("0") ) {
							value = "D";//discontinue
						} else {
							value = "C";
						}
						e2EUtil.jsSelect("childExpense["+i+"].expense['"+feeInput[j]+"'][0].status",value);
						
						e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].amount",feeArray[1]);
						e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].adjustAmount",feeArray[2]);
						

						/*
						e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].description",feeArray[0]);
						e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].amount",feeArray[1]);
						value = feeArray[2];
						if ( feeArray[0].equals("0") ) {
							value = "N";//no change
						} else if ( feeArray[0].equals("1") ) {
							value = "D";//discontinue
						} else {
							value = "C";
						}
						e2EUtil.jsSelect("childExpense["+i+"].expense['"+feeInput[j]+"'][0].status",value);
						
						if ( feeArray.length>=4 ) {
							e2EUtil.jsInput("childExpense["+i+"].expense['"+feeInput[j]+"'][0].adjustAmount",feeArray[3]);
						}*/
					}
				}
								
				//new fee
				boolean hasNewFee = false;
				for(int j=0;j<3;j++) {
					key = "newFee"+(j+1);
					feeValue = e2EUtil.jsonString(jsonModel, key);
					if ( feeValue==null || feeValue.equals("") ) {
						continue;
					} else {
						if ( !hasNewFee ) {
							hasNewFee = true;

							this.waitIt();
							//e2EUtil.fluentWait(By.name("childExpense["+i+"].addSE"));
							//why there is no expense sometimes
							e2EUtil.jsRadio("childExpense["+i+"].addSE", "true");
						}
					}

					feeArray = feeValue.split("&");
					if ( feeArray.length==2 ) {
						e2EUtil.jsInput("childExpense["+i+"].expense['"+newFeeInput[j]+"'][0].description",feeArray[0]);
						e2EUtil.jsInput("childExpense["+i+"].expense['"+newFeeInput[j]+"'][0].amount",feeArray[1]);
					}
				}

				if ( !hasNewFee ) {
					e2EUtil.jsRadio("childExpense["+i+"].addSE", "false");
				}
			}
		}
		
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
		this.waitIt();
	}

	protected String checkApplicationFee(JSONObject jsonObject, WebDriver driver, E2EUtil e2EUtil) throws Exception {
		String result = "";
		WebElement element = null;

		try {
			element = driver.findElement(By.id("acsc_costFee"));
		} catch (Exception e) {
			throw new CheckException("Can not go to summary page");
		}
		
		//"$"+
		String value = e2EUtil.jsonString(jsonObject.getJSONObject("fee"), "expectedFee");
		String expectedFee = null;//
		if ( value.equals("0") ) {
			expectedFee = "$0.00";
		} else {
			expectedFee = "$"+value+".00";
		}
		//be $80!But 80,00$!
		//be 80,00$!But $80.00!
		//be 0,00$!But $.00!
		if ( !expectedFee.equals(element.getText()) ) {
			//danger
			throw new CheckException("Non-refundable service fee should be "+expectedFee+"!But "+element.getText()+"!");
		}
		
		result = "Non-refundable service fee:"+element.getText()+"<br/>";
		return result;
	}

	protected String checkApplicationSubmit(JSONObject jsonObject, WebDriver driver, E2EUtil e2EUtil) throws Exception {
		String result = "";
		WebElement element = null;
		List<WebElement> elementList;

		this.checkPageError();
		
		try {
			e2EUtil.fluentWait(By.cssSelector(".Data div"));
			
			//element = driver.findElement(By.cssSelector(".Data div"));
		} catch (Exception e) {
			throw new CheckException("Can not go to last page");
		}
		
		elementList = driver.findElements(By.cssSelector(".Data div"));
		String str = "";
		String transId = "";
		for (int i=0;i<elementList.size();i++) {
			str = elementList.get(i).getText();
			result += str+"<br/>";
			if ( str.indexOf("Reference number:")>=0 ) {
				transId = str.substring( str.indexOf("Reference number:")+"Reference number:".length() );
			}
		}
		if ( !transId.equals("") ) {
			System.out.println( "transId:"+transId+"!" );
			//this.getXML(transId);
			//result += "XML:<![CDATA["+this.getXML(transId)+"!]]><br/>";
			result += "<textarea style='margin: 0px; width: 600px; height: 90px;'>"+this.getXML(transId)+"</textarea><br/>";
		}
		
		return result;
	}
	
	private String getXML(String transId) throws SQLException {
    	Connection myConnection = null;
    	Statement sqlStatement = null;
    	
    	String result = "";
        try { 
            String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=susracdev.service.cihs.gov.on.ca)(PORT=1525)))(CONNECT_DATA=(SERVICE_NAME=susdev)))";
            String strUserID = "s2i";
            String strPassword = "s2i";
            
            myConnection=DriverManager.getConnection(dbURL,strUserID,strPassword);
            sqlStatement = myConnection.createStatement();
            
            String readRecordSQL = "select * from s2i_xml_log where transaction_id = "+transId+" and xml_type like '%CSSCheckOut [EXIT] (Backend Request)'";  
            ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
            int max=1000;
            while (myResultSet.next()) {
                System.out.println("Record Max: " + myResultSet.getString("data_xml"));
                result = myResultSet.getString("data_xml");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        	if ( sqlStatement!=null ) {
        		sqlStatement.close();
        		sqlStatement = null;
        	}
        	if ( myConnection!=null ) {
        		myConnection.close();
        		myConnection = null;
        	}
        }
        
        return result;
	}

	protected void fillApplicationFee(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		this.waitIt();

		//may need additional sin here
		elementList = driver.findElements( By.name("applicant.SIN") );
		if ( elementList.size()>=1 ) {
			this.checkPageError();
			
			value = e2EUtil.jsonString(jsonObject, "additionalSin");
			if ( "".equals(value) || value==null ) {
				//value = "000000018";
				throw new CheckException("Please input additional sin in test case!");
			}
			e2EUtil.jsInput("applicant.SIN", value);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();
			this.waitIt();
		} else {
			value = e2EUtil.jsonString(jsonObject, "additionalSin");

			if ( value!=null && !value.trim().equals("") && !value.trim().equals("NO SIN") ) {
				//value = "000000018";
				throw new CheckException("There should be one input for additional sin!");
			}
		}
		
		this.checkPageError();
		
		try {
			jsonModel = jsonObject.getJSONObject("fee");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		e2EUtil.fluentWait(By.name("primary"));
		
		if ( !gotIt ) {
		} else {
			key = "skip";
			value = e2EUtil.jsonString(jsonModel, key);
			if ( "true".equals(value) ) {
				e2EUtil.js("$('#acscp1_SkipFeeWaiver').click();");
				return;
			}
			
			key = "primary";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			
			this.waitIt();
			
			key = "liquid";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "networth";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "numOfInHouse";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
			key = "grossRange";
			value = e2EUtil.jsonString(jsonModel, key);
			e2EUtil.jsRadio(key, value);
		}
		
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
		this.waitIt();
	}

	protected void fillApplicationFileUpload(JSONObject jsonObject) throws Exception {
		JSONObject jsonModel = null;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		this.checkPageError();
		
		try {
			jsonModel = jsonObject.getJSONObject("fileUpload");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//sometime the file upload will go summary directly
		try {
			driver.findElement(By.id("acsc_costFee"));
			return;
		} catch (Exception e) {
		}
		//system exception
		try {
			element = driver.findElement(By.name("responseJson"));
		} catch (Exception e) {
			throw new CheckException("Can not go to file upload page.");
		}
		//for wait
		e2EUtil.fluentWait(By.className("FileCreateInput"));//.FileCreateInput name=rule-1-1
		
		//TODO By.className("FileCreateInput") to group
		if ( !gotIt ) {
			//add extra file
            elementList = driver.findElements(By.className("FileCreateInsert"));
            for ( int i=0;i<elementList.size();i++ ) {
            	element = elementList.get(i);
            	try {
                	element.click();
            	} catch ( Exception e ) {
            		System.out.println("FileCreateInsert:"+i);
            	}
            }
            
            if ( elementList.size()==0 ) {
            	throw new Exception("There should be at least one File Input!");
            }
			
            elementList = driver.findElements(By.className("FileCreateInput"));
            
            for ( int i=0;i<elementList.size();i++ ) {
            	if (i%3!=0) {
                	element = elementList.get(i);
                	//TODO config
                	//test.pdf S2I_FileUploadEAR.jpg
                	String fileName = "C:\\green\\e2e\\test"+(i%3)+".pdf";

            		//http://stackoverflow.com/questions/11256732/how-to-handle-windows-file-upload-using-selenium-webdriver
                	//element.click();
                	//driver.switchTo().activeElement().sendKeys(fileName);
                	
                	element.sendKeys(fileName);
            	}
            }
            elementList = driver.findElements(By.id("chkUpload"));
            if ( elementList.size()>=1 ) {
            	element = elementList.get(0);
            	element.click();
            }
            String js = "if( $('#chkUpload').prop('checked')=='false' ) { $('#chkUpload').prop('checked','true') }";
            e2EUtil.js(js); 
		} else {
		}
		
		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();
		this.waitIt();
	}
	
	protected void fillApplicationSubmit(JSONObject jsonObject) throws InterruptedException {
		JSONObject jsonModel = jsonObject;
		String key,value;
		boolean gotIt = false;
		WebElement element;
		List<WebElement> elementList;
		
		//key = "liquid";
		//value = e2EUtil.jsonString(jsonModel, key);
		e2EUtil.jsCheckbox("certified", "true");

		this.waitIt();
		//this.waitIt();

		element = driver.findElement(By.className("s2iNavigateAway"));
		element.click();

		this.waitIt();
		//this.waitIt();
		elementList = driver.findElements(By.name("paymentType"));
		if ( elementList.size()>0 ) {
			//pay money
			key = "paymentType";
			value = e2EUtil.jsonString(jsonModel, key);
			String paymentType = "Interac";//value  Interac   CreditCard
			e2EUtil.jsRadio(key, paymentType);

			element = driver.findElement(By.className("s2iNavigateAway"));
			element.click();

	        e2EUtil.js("$('#confirmPaymentButton').click();");

	        //Thread.sleep(1000);
			//e2EUtil.jsAlert();
	        //Thread.sleep(1000);

	        if ( "CreditCard".equals(paymentType) ) {
				e2EUtil.jsInput("trnCardOwner", "andy");
				e2EUtil.jsInput("trnCardType", "MC");
				key = "paymentAmount";
				value = e2EUtil.jsonString(jsonModel, key);
				e2EUtil.jsInput("trnCardNumber", value);
				e2EUtil.jsSelect("trnExpYear", "18");
				e2EUtil.jsInput("trnCardCvd", "123");

				element = driver.findElement(By.name("submitButton"));
				element.click();

				e2EUtil.jsAlert();

				element = driver.findElement(By.name("submitButton"));
				element.click();
	        } else {
				element = e2EUtil.fluentWait(By.name("btnBank"));
				element.click();

				element = e2EUtil.fluentWait(By.name("btnLogin"));
				element.click();
				
				element = e2EUtil.fluentWait(By.name("btnsend"));
				element.click();
				
				element = e2EUtil.fluentWait(By.name("btnnext"));
				element.click();
				
				System.out.println("debit card can be empty");
	        }
		}
		
		//for wait
		/**/
		String consent = e2EUtil.jsonString(jsonModel, "downloadConsent");
		if ( "true".equals(consent) ) {
			try {
				e2EUtil.fluentWait(By.name("printOrEmail"));
				e2EUtil.jsCheckbox("printOrEmail", "email");

				element = driver.findElement(By.className("s2iNavigateAway"));
				element.click();

		        Thread.sleep(500);
				e2EUtil.jsAlert();
				
			} catch (Exception e) {
				throw new CheckException("There is no consent returned!");
			}
		} else if ( "false".equals(consent) ) {
			try {
				e2EUtil.fluentWait(By.id("acsc_id-email"));
			} catch (Exception e) {
				elementList = driver.findElements(By.name("printOrEmail"));
				if ( elementList.size()>0 ) {
					throw new CheckException("There is consent returned!");
				} else {
					throw new CheckException("Can not go to confirmation page!");
				}
			}
			
		} else {
			try {
				e2EUtil.fluentWait(By.name("printOrEmail"));
				e2EUtil.jsCheckbox("printOrEmail", "email");

				element = driver.findElement(By.className("s2iNavigateAway"));
				element.click();
			} catch (Exception e) {
				System.out.println("There is no email send page.");
			}
		}

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
