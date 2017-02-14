package ca.on.gov.gsc.s2i.e2e.bf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.tool.SeleniumBase;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;
import ca.on.gov.gsc.s2i.e2e.util.WebDriverUtil;

//wait
//test case
//run in official
//time out
public abstract class SeleniumBF extends SeleniumBase {
	public SeleniumBF(String inIdPattern, String inCloseEvent) {
		super(inIdPattern, inCloseEvent);
	}

	@Override
	protected void globalConfig(AutomationConfig automationConfig) {
		automationConfig.setDriverType( PropertyBF.getInstance().getProperty("s2i.bf.driverType") );
		automationConfig.setLocalDic( PropertyBF.getInstance().getProperty("s2i.bf.localDic") );
		automationConfig.setRootUrl( PropertyBF.getInstance().getProperty("s2i.bf.rootUrl") );
		
		automationConfig.setResultDic( PropertyBF.getInstance().getProperty("s2i.bf.resultDic") );
		automationConfig.setTestDataDic( PropertyBF.getInstance().getProperty("s2i.bf.testDataDic") );
		
		automationConfig.setSkipPositive( PropertyBF.getInstance().getProperty("s2i.bf.skipPositive") );
		automationConfig.setSkipNegative( PropertyBF.getInstance().getProperty("s2i.bf.skipNegative") );
		
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

	private Map<String, String > mapQuestion = new HashMap<String, String>(){{
        put("q1","1_AppAge");
        put("q2","2_Income");
        put("q3","3_Working");
        put("q4","4_Training");
        put("q5","5_FamSize");
        put("q6","6_Children");
        put("q7","7_ChildAge");
        put("q8","8_ChildHealth");
        put("q9","9_AppHealth");
        put("q10","10_Student");
        put("q11","11_Housing");
        put("q12","12_HouseAssist");
        put("q13","13_LegalAssist");
        put("q14","14_FinAssistYN");
        put("q15","15_FinAssist");
    }};

	private Map<String, String > mapCheck = new HashMap<String, String>(){{
		/*
        put("CCFS","Child care fee subsidy is a program that helps qualifying families cover the cost of licensed child care for children up to the age of 12");
        put("HSO","The Healthy Smiles Ontario program covers regular visits to a licensed dental care provider, such as a dentist or dental hygienist, to establish and maintain good oral health. It covers a full range of preventive and early treatment dental services including check-ups, cleaning, fillings, x-rays, scaling and more");
        put("TSALC","Targetted Subsidies for Adoption and Legal Custody");
        put("TCA","Temporary Care Assistance");
        put("GAINS","Ontario Guaranteed Annual Income System");//
        put("ODB","Ontario Drug Benefit");
        put("SCPP","Seniors Co-Payment Program");
        put("ACSD","Assistance for Children with Severe Disabilities");
        put("ODSP","Ontario Disability Support Program");
        put("ADP","Assistive Devices Program");
        put("DSH","Dedicated Supportive Housing");
        put("HS","Habitat Services");
        put("HVMP","Home and Vehicle Modification Program");
        put("HSC","Homes for Special Care");
        put("LTCH","Long-Term Care Homes");
        put("MHSH","Mental Health Supportive Housing");
        put("TDP","The TDP helps people who have high prescription drug costs relative to their household income");
        put("ETAARW","Employment Training for Abused and At-Risk Women");
        put("MWOP","Microlending for Women in Ontario Program");
        put("OW","Ontario Works");
        put("SC","Second Career");
        put("WIST/IT","Women In Skilled Trades and Information Technology Training");
        put("HHPP","Housing and Homelessness Prevention Programs");
        put("OSAP","The Ontario Student Assistance Program");
        put("OTG","Ontario Tuition Grant 30% off Ontario Tuition");
        put("FMIS","Family Mediation and Information Services");
        put("FW","Fee Waiver");
        put("MMP","Mandatory Mediation Program");*/

        put("CCFS","Child Care Fee Subsidy");
        put("HSO","Healthy Smiles Ontario");//HSO- 
        put("TSALC","Targeted Subsidies for Adoption and Legal Custody");
        put("TCA","Temporary Care Assistance");
        put("GAINS","GAINS: Ontario Guaranteed Annual Income System");//
        put("ODB","Ontario Drug Benefit");
        //put("SCPP","Seniors Co-Payment Program");
        put("SCPP","Reduced Co-payment for Lower Income Seniors");
        put("ACSD","Assistance for Children with Severe Disabilities");
        put("ODSP","Ontario Disability Support Program");
        put("ADP","Assistive Devices Program");
        put("DSH","Dedicated Supportive Housing");
        put("HS","Habitat Services");
        put("HVMP","Home and Vehicle Modification Program");
        put("HSC","Homes for Special Care");
        put("LTCH","Long-Term Care Homes");
        put("MHSH","Mental Health Supportive Housing");
        put("TDP","Trillium Drug Program");
        put("ETAARW","Employment Training for Abused / at-Risk Women");
        put("MWOP","Microlending for Women in Ontario Program");
        put("OW","Ontario Works");
        put("SC","Second Career");
        put("WIST IT","Women in Skilled Trades and Information Technology Training");
        put("WIST/IT","Women in Skilled Trades and Information Technology Training");
        
        put("HHPP","Housing and Homelessness Prevention Programs");
        put("AHHPP","Affordable Housing and Homelessness Prevention Programs");

        //put("OSAP","OSAP - Ontario Student Assistance Program");
        put("OSAP","Ontario Student Assistance Program");
        //put("OTG","OTG - Ontario Tuition Grant 30% off Ontario Tuition");
        put("OTG","30% Off Ontario Tuition");
        put("FMIS","Family Mediation and Information Services");
        put("FW","Fee Waiver");
        put("MMP","Mandatory Mediation Access Plan");//Mandatory Mediation Program
    }};
    
    protected void jsClickByNameIndex(JSONObject jsonObject,String key) {
		String value = e2EUtil.jsonString(jsonObject, key);
		if ( value!=null && !value.equals("") ) {
		} else {
			value = "A";
		}

		//TODO to util
		//TODO check string
		//TODO support multiple
		String inputName = mapQuestion.get(key);
		
		/* if use number
		try {
			int valueInt = Integer.parseInt(value)-1;
			value = String.valueOf(valueInt);
			String js;
			js = "$(\"[name='"+inputName+"']\").eq("+value+").click();";
			e2EUtil.js(js);
			js = "$(\"[name='"+inputName+"'] option\").eq("+(valueInt+1)+").prop('selected', true);";
			e2EUtil.js(js);
		} catch (Exception e) {
			System.out.println(e);
		}*/
		WebElement element;
		
		//use multiple letter
		String valueSub = null;
		int valueInt = 0;
		for ( int i=0;i<value.length();i++ ) {
			valueSub = value.substring(i,i+1);
			if ( "A".equalsIgnoreCase(valueSub) ) {
				valueInt = 0;
			} else if ( "B".equalsIgnoreCase(valueSub) ) {
				valueInt = 1;
			} else if ( "C".equalsIgnoreCase(valueSub) ) {
				valueInt = 2;
			} else if ( "D".equalsIgnoreCase(valueSub) ) {
				valueInt = 3;
			} else if ( "E".equalsIgnoreCase(valueSub) ) {
				valueInt = 4;
			} else if ( "F".equalsIgnoreCase(valueSub) ) {
				valueInt = 5;
			} else if ( "G".equalsIgnoreCase(valueSub) ) {
				valueInt = 6;
			} else if ( "H".equalsIgnoreCase(valueSub) ) {
				valueInt = 7;
			} else if ( "I".equalsIgnoreCase(valueSub) ) {
				valueInt = 8;
			} else if ( "J".equalsIgnoreCase(valueSub) ) {
				valueInt = 9;
			}

			try {
				element = e2EUtil.fluentWait(By.name(inputName));
				
				if ( "6_Children".equals(inputName) ) {
					System.out.print("trying:"+inputName+" ");
				}
				
				String js;
				js = "var ele  = $(\"[name='"+inputName+"']\").eq("+valueInt+");if (!ele.prop('checked')) {ele.click();};";
				//System.out.println(js);
				e2EUtil.js(js); 
				js = "$(\"[name='"+inputName+"'] option\").eq("+(valueInt+1)+").prop('selected', true);";
				e2EUtil.js(js);
			} catch (Exception e) {
				System.out.println("some error is on:"+inputName);
				System.out.println(e);
			}
		}
	}

	protected void fillPostCode(JSONObject jsonObject,int buttonIdx) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String key,value;
		
		//this.checkPageError();
		element = e2EUtil.fluentWait(By.name("14_FinAssistYN"));
		
		//clean history,must put all checkbox unchecked.
		String js = "$('input:checkbox').removeAttr('checked');";
		//System.out.println(js);
		e2EUtil.js(js); 

		for ( int i=1;i<16;i++ ) {
			key = "q"+i;
			this.jsClickByNameIndex(jsonObject, key);
			//this.waitItShort();
		}

		this.waitIt();

		element = e2EUtil.fluentWait(By.className("Primary"));
		element.click();//s2iNavigateAway
	}

	protected String fillPostCodeCheck(String resultJson,boolean exist) throws Exception {
		List<WebElement> elementList;
		WebElement element;
		String result = "";
		
		System.out.println(resultJson);
		if ( mapCheck.get(resultJson)==null ) {
			throw new CheckException(resultJson+" is not in mapCheck!");
		} else {
			resultJson = mapCheck.get(resultJson);
		}
		
		this.checkPageError();

		for ( int i=0;i<10;i++ ) {
			element = e2EUtil.fluentWait(By.cssSelector("h4.Title a"));
			if ( element.getText().equals("") ) {
				this.waitIt();
			} else {
				break;
			}
		}

		//mailingAddressJson  residentialAddressJson
		elementList = driver.findElements(By.cssSelector("h4.Title a"));
		if ( elementList.size()==0 ) {
			throw new CheckException("get invalid address");
		} else {
			for (int i=0;i<elementList.size();i++) {
				element = elementList.get(i);
				result += element.getText()+".";
			}
		}
		
		if ( exist ) {
			if ( result.indexOf(resultJson)<0 ) {
				throw new CheckException("There is no benefit for "+resultJson+"!("+result+")");
			}
		} else {
			if ( result.indexOf(resultJson)>=0 ) {
				throw new CheckException("There is benefit for "+resultJson+"!("+result+")");
			}
		}

		//result = "";
		if ( exist ) {
			result = "There is benefit for "+resultJson+"!"+"<br/>";//("+result+")
		} else {
			result = "There is no benefit for "+resultJson+"!"+"<br/>("+result+")";//("+result+")
		}
		return result;
	}
}
