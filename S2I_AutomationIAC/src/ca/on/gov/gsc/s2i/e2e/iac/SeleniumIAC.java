package ca.on.gov.gsc.s2i.e2e.iac;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.tool.SeleniumBase;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;
import ca.on.gov.gsc.s2i.e2e.util.WebDriverUtil;
import ca.ontario.health.iac.util.Exception_Exception;
import ca.ontario.health.iac.util.IACCleanWSService;

//wait
//test case
//run in official
//time out
public abstract class SeleniumIAC extends SeleniumBase {
	public SeleniumIAC(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		automationConfig.setDriverType( PropertyIAC.getInstance().getProperty("s2i.iac.driverType") );
		automationConfig.setLocalDic( PropertyIAC.getInstance().getProperty("s2i.iac.localDic") );
		automationConfig.setRootUrl( PropertyIAC.getInstance().getProperty("s2i.iac.rootUrl") );
		
		automationConfig.setResultDic( PropertyIAC.getInstance().getProperty("s2i.iac.resultDic") );
		automationConfig.setTestDataDic( PropertyIAC.getInstance().getProperty("s2i.iac.testDataDic") );

		automationConfig.setSkipPositive( PropertyIAC.getInstance().getProperty("s2i.iac.skipPositive") );
		automationConfig.setSkipNegative( PropertyIAC.getInstance().getProperty("s2i.iac.skipNegative") );
		
		automationConfig.setWindowWidth( 1024 );
		automationConfig.setWindowHeight( 900 );
		
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
		//Thread.sleep(500);//check result;
		
		WebDriverUtil webDriverUtil = WebDriverUtil.getInstance();
		
		AutomationConfig config = webDriverUtil.getAutomationConfig();

		//TODO put to other place
        driver.get( config.getRootUrl() );
        //TODO try to deal with 404
        //int code = webClient.getPage("http://your.url/123/").getWebResponse().getStatusCode();
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        e2EUtil.jsAlert();

		//List<WebElement> elementList;
		WebElement element;
		//List<WebElement> elementList;

		//for ie https
        //this.waitIt();
        //elementList = driver.findElements(By.name("actionIcon"));
        //e2EUtil.js("document.getElementById('overridelink').click()");
		//Thread.sleep(10000);//check result;

        this.checkPageError();
		
		e2EUtil.fluentWait(By.className("s2iNavigateAway")).click();
	}
	protected void jsInputById(JSONObject jsonObject,String key) {
		String value = e2EUtil.jsonString(jsonObject, key);
		if ( value!=null && !value.equals("") ) {
			e2EUtil.jsInputById(key, value);//value
		}
	}
	protected void jsSelectById(JSONObject jsonObject,String key) throws Exception {
		String value = e2EUtil.jsonString(jsonObject, key);
		if ( value!=null && !value.equals("") ) {
			if ( "unitType".equals(key) ) {
				if ( value.equals("Apartment") ) {
					value = "APT";
				} else if ( value.equals("Suite") ) {
					value = "SUITE";
				} else if ( value.equals("Unit") ) {
					value = "UNIT";
				} else if ( value.equals("Appartement") ) {
					value = "APP";
				} else if ( value.equals("Bureau") ) {
					value = "BUREAU";
				} else if ( value.equals("Unité") || value.equals("Unit?") ) {
					value = "UNITE";
				} else {
					throw new Exception("no match value for:"+key+":"+value);
				}
			} else if ( "streetNumSuffix".equals(key) ) {
				if ( value.equals("1/4") ) {
					value = "1";
				} else if ( value.equals("1/2") ) {
					value = "2";
				} else if ( value.equals("3/4") ) {
					value = "3";
				}
			} else if ( "streetDirection".equals(key) ) {
				if ( value.equals("North") ) {
					value = "N";
				} else if ( value.equals("South") ) {
					value = "S";
				} else if ( value.equals("East") ) {
					value = "E";
				} else if ( value.equals("West") ) {
					value = "W";
				} else if ( value.equals("North-east") ) {
					value = "NE";
				} else if ( value.equals("North-west") ) {
					value = "NW";
				} else if ( value.equals("South-east") ) {
					value = "SE";
				} else if ( value.equals("South-west") ) {
					value = "SW";
				} else if ( value.equals("Nord") ) {
					value = "N";
				} else if ( value.equals("Sud") ) {
					value = "S";
				} else if ( value.equals("Est") ) {
					value = "E";
				} else if ( value.equals("Ouest") ) {
					value = "O";
				} else if ( value.equals("Nord-Est") ) {
					value = "NE";
				} else if ( value.equals("Nord-Ouest") ) {
					value = "NO";
				} else if ( value.equals("Sud-Est") ) {
					value = "SE";
				} else if ( value.equals("Sud-Ouest") ) {
					value = "SO";
				} else {
					throw new Exception("no match value for:"+key+":"+value);
				}
			} else if ( "lotType".equals(key) ) {
				if ( value.equals("LOT") ) {
					value = "L";
				} else if ( value.equals("PART LOT") ) {
					value = "P";
				} else if ( value.equals("BLOCK") ) {
					value = "B";
				} else if ( value.equals("PART BLOCK") ) {
					value = "K";
				} else if ( value.equals("LOT") ) {
					value = "L";
				} else if ( value.equals("LOT DE PARTIE") ) {
					value = "P";
				} else if ( value.equals("BLOC") ) {
					value = "B";
				} else if ( value.equals("BLOC DE PARTIE") ) {
					value = "K";
				} else {
					throw new Exception("no match value for:"+key+":"+value);
				}
			}

			e2EUtil.jsSelectById(key, value);//value
		}
	}

	protected void fillContact(JSONObject jsonObject) throws Exception {
		JSONObject jsonContact = null;
		String key,value;
		boolean gotIt = false;
		List<WebElement> elementList;
		WebElement element;
		
		checkPageError();
		
		try {
			jsonContact = jsonObject.getJSONObject("contact");
			gotIt = true;
		} catch ( Exception e ) {
		}

		if ( gotIt ) {
			String prefix = "contactInfo.";
			
			key = "firstName";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "lastName";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "homephone";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "phone";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "extension";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "email";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
			key = "emailConfirmation";
			value = e2EUtil.jsonString(jsonContact, key);
			e2EUtil.jsInput(prefix+key, value);
		}
		
		elementList = driver.findElements(By.name("certified"));
		if ( elementList.size()>0 ) {
			element = elementList.get(0);
			element.click();
		}
		elementList = driver.findElements(By.className("s2iNavigateAway"));
		for ( WebElement elementSingle: elementList) {
			if ( "Submit".equals(elementSingle.getText()) ) {
				elementSingle.click();
				break;
			}
		}
	}

	protected String fillHealthCard(JSONObject jsonObject) throws Exception {
		JSONObject jsonCard = null;
		String key,value;
		boolean gotIt = false;

		this.checkPageError();
		
		String cardNo = "";
		
		try {
			jsonCard = jsonObject.getJSONObject("card");
			gotIt = true;
		} catch ( Exception e ) {
		}

		//for wait
		e2EUtil.fluentWait(By.name("healthCardType"));
		
		if ( !gotIt ) {
			/*
			cardNo = "2222222222";
			
			e2EUtil.jsRadio("healthCardType", "PHOTO");
			e2EUtil.jsInput("healthCardNumber", cardNo);
			e2EUtil.jsInput("healthCardVersionCode", "AH");

			e2EUtil.jsInput("lastPostalCodeMOH", "M2K2P8");
			e2EUtil.jsInput("lastFourDigit", "1234");*/

			cardNo = "6655890066";
			
			e2EUtil.jsRadio("healthCardType", "PHOTO");
			e2EUtil.jsInput("healthCardNumber", cardNo);
			e2EUtil.jsInput("healthCardVersionCode", "VY");

			e2EUtil.jsInput("lastPostalCodeMOH", "M2K2P8");
			e2EUtil.jsInput("lastFourDigit", "8885");
		} else {
			key = "healthCardType";
			value = e2EUtil.jsonString(jsonCard, key);
			e2EUtil.jsRadio(key, value);
			key = "healthCardNumber";
			value = e2EUtil.jsonString(jsonCard, key);
			
			cardNo = value;
			
			e2EUtil.jsInput(key, value);
			key = "healthCardVersionCode";
			value = e2EUtil.jsonString(jsonCard, key);
			e2EUtil.jsInput(key, value);

			key = "lastPostalCodeMOH";
			value = e2EUtil.jsonString(jsonCard, key);
			e2EUtil.jsInput(key, value);
			key = "lastFourDigit";
			value = e2EUtil.jsonString(jsonCard, key);
			e2EUtil.jsInput(key, value);
		}

		if ( !"".equals(cardNo) ) {
			IACCleanWSService wsClient = new IACCleanWSService();
			try {
				//9984152588
				//2222222222
				//9954113321
				wsClient.getIACCleanWSPort().cleanHealthNumber(cardNo);
			} catch (Exception_Exception e) {
				e.printStackTrace();
			}
		}

		e2EUtil.jsRadio("doMto", "N");
		driver.findElement(By.className("s2iNavigateAway")).click();
		
		return cardNo;
	}

	protected void fillPostCode(JSONObject jsonObject,int buttonIdx) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String key,value;
		
		this.checkPageError();
		
		elementList = driver.findElements(By.className("Secondary"));
		element = elementList.get(buttonIdx);
		element.click();
		
		this.checkPageError();
		
		key = "q3i1";
		value = e2EUtil.jsonString(jsonObject, key);
		if ( value!=null ) {
			//element = driver.findElement(By.id(key));
			element = e2EUtil.fluentWait(By.id(key));
			this.waitIt();
			element.sendKeys( value );
		} else {
			throw new CheckException("Please input post code!");
		}

		//PostalCodeConfirmTitle
		/*
		elementList = driver.findElements(By.id("PostalCodeConfirmBodyMessage"));
		if ( elementList.size()>0 ) {
			element = elementList.get(0);
			System.out.println( element.getText() );
			//javascript:S2i.PostalCodeLookup.PostalCodeConfirmYes();
		}*/

		element = driver.findElement(By.id("btnLookupPC"));
		element.click();
		
		this.checkPageError();

		//PostalCodeConfirmTitle
		elementList = driver.findElements(By.id("PostalCodeConfirmBodyMessage"));
		if ( elementList.size()>0 ) {
			element = elementList.get(0);
			if ( element.getText()!=null && !element.getText().trim().equals("") ) {
				System.out.println( element.getText() );
				e2EUtil.js("S2i.PostalCodeLookup.PostalCodeConfirmYes();");
				this.waitIt();
			}
		}

		//section 2 3  name--id
		key = "addresses";
		value = e2EUtil.jsonString(jsonObject, key);
		value = value.trim().replace(" ", "-");
		if ( value!=null && !value.equals("") ) {
			//element = driver.findElement(By.id(value));
			//element.click();
			e2EUtil.fluentWait(By.id(value));
			
			e2EUtil.js("$('#"+value+"').click();");
			this.waitIt();

			elementList = driver.findElements(By.id("PostalCodeConfirmBodyMessage"));
			if ( elementList.size()>0 ) {
				element = elementList.get(0);
				if ( element.getText()!=null && !element.getText().trim().equals("") ) {
					System.out.println( element.getText() );
					e2EUtil.js("S2i.PostalCodeLookup.PostalCodeConfirmYes();");
					this.waitIt();
				}
			}

			key = "rdoReAddressType";
			value = e2EUtil.jsonString(jsonObject, key);
			if ( value!=null && !value.equals("") ) {
				//e2EUtil.js("$('#"+value+"').click();");
				e2EUtil.radioById(value);
				this.waitIt();
			}
		}

		key = "unitType";
		this.jsSelectById(jsonObject, key);
		//e2EUtil.js("S2i.PostalCodeLookup.Validators.UnitType();");
		key = "unitNumber";
		this.jsInputById(jsonObject, key);
		key = "streetNumber";
		this.jsInputById(jsonObject, key);
		key = "streetNumSuffix";
		this.jsSelectById(jsonObject, key);
		key = "streetName";
		this.jsInputById(jsonObject, key);
		key = "streetType";
		this.jsInputById(jsonObject, key);
		key = "streetDirection";
		this.jsSelectById(jsonObject, key);
		key = "municipality";
		this.jsInputById(jsonObject, key);

		key = "lotType";
		this.jsSelectById(jsonObject, key);
		key = "lotNumber";
		this.jsInputById(jsonObject, key);
		key = "concessionNumber";
		this.jsInputById(jsonObject, key);
		key = "planNumber";
		this.jsInputById(jsonObject, key);
		key = "township";
		this.jsInputById(jsonObject, key);
		
		//poBoxNumber etc for mail address
		key = "poBoxNumber";
		this.jsInputById(jsonObject, key);

		e2EUtil.js("S2i.PostalCodeLookup.Confirm();");
		this.waitIt();
	}

	protected String fillPostCodeCheck(String resultJson) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String result = null;
		
		this.checkPageError();

		//mailingAddressJson  residentialAddressJson
		elementList = driver.findElements(By.name(resultJson));
		if ( elementList.size()==0 ) {
			throw new CheckException("get invalid address");
		} else {
			element = elementList.get(0);
			result = element.getAttribute("value");
			if ( "".equals(result) ) {
				throw new CheckException("get invalid address");
			}

			//deal with json
			int i = result.indexOf("description\":\"");
			int j = result.indexOf("\"",i+14);
			if ( i>0 && j>0 && j>(i+14) ) {
				result = result.substring(i+14,j);
			}
		}

		return result+"<br/>";
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

	protected String fillSummaryCheck(String cardNo) throws Exception {
		String result = "";
		
		WebElement element;
		List<WebElement> elementList;
		
		checkPageError();

		element = e2EUtil.fluentWait(By.id("s2irefTransactionID"));
		result += element.getText()+"<br/>";

		boolean gotIt = false;
		elementList = driver.findElements(By.tagName("td"));
		for ( WebElement elementSingle: elementList) {
			if ( "itemDetails".equals(elementSingle.getAttribute("headers")) ) {
				//System.out.println( elementSingle.getText() );
				if ( elementSingle.getText()!=null && !elementSingle.getText().equals("") ) {
					String str = elementSingle.getText();

					if ( str.indexOf("Status: Failed")>=0 ) {
						//str = str.replaceAll("Status: Failed", "<span style='color:red'>Status: Failed</span>");
						throw new CheckException("Status: Failed. Sorry, we are not able to proceed your Address Change Request for this product, please check with Service Ontario office.("+result+")");
					}
					
					result += str+"<br/>";
					gotIt = true;
				}
				break;
			}
		}
		if ( !gotIt ) {
			throw new CheckException("Can not get order in several seconds.");
		} else {
			//clean
			if ( !"".equals(cardNo) ) {
				IACCleanWSService wsClient = new IACCleanWSService();
				try {
					//9984152588
					//2222222222
					//9954113321
					wsClient.getIACCleanWSPort().cleanHealthNumber(cardNo);
				} catch (Exception_Exception e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
