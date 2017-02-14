package ca.on.gov.gsc.s2i.e2e.tool;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.model.AutomationConfig;
import ca.on.gov.gsc.s2i.e2e.model.E2ECaseResult;
import ca.on.gov.gsc.s2i.e2e.model.E2ELoopResult;
import ca.on.gov.gsc.s2i.e2e.util.CheckException;
import ca.on.gov.gsc.s2i.e2e.util.DateUtil;
import ca.on.gov.gsc.s2i.e2e.util.E2EUtil;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;
import ca.on.gov.gsc.s2i.e2e.util.WebDriverUtil;

@RunWith(Parameterized.class)
public abstract class SeleniumBase {
	protected WebDriver driver;
	protected E2EUtil e2EUtil;
	protected String jsonFile;
	protected String resultId = null;
	
	//useless
	protected int mode=1;//0 debug single 1 loop run

	protected abstract String runCase(JSONObject jsonObject) throws Exception;

	protected abstract void restartRoot() throws Exception;
	
	protected abstract void globalConfig(AutomationConfig automationConfig);

	protected AutomationConfig createConfig() {
		AutomationConfig automationConfig = new AutomationConfig();
		
		globalConfig(automationConfig);
		
		//here can check and set default value
		
		return automationConfig;
	}

	/*
	@AfterClass
	public static void tearDownClass() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}*/

	@Before
	public void setUp() throws Exception {
		AutomationConfig automationConfig = createConfig();
		WebDriverUtil.getInstance().setAutomationConfig(automationConfig);
		driver = WebDriverUtil.getInstance().initDriver();

		if ( e2EUtil==null ) {
			e2EUtil = E2EUtil.getInstance(WebDriverUtil.getInstance().getDriver());
		}

		if ( "keep".equals(this.closeEvent) ) {
			//kept for check, when multiple scenario run together.
		    WebDriverUtil.getInstance().blockDriver("Keep Window");
		}
		
		WebDriverUtil.getInstance().blockDriver(jsonFile);
		//json/gson jsonFile/resFile template/here                  copy small open-close read

        this.restartRoot();
	}
	
	@After
	public void tearDown() throws Exception {
		e2EUtil.log("tearDownClass()");
		WebDriverUtil.getInstance().cleanDriver(jsonFile);
	}
	
	public void testCaseArray11(String afd) throws Exception {
		
	}
	
	//scenario level parameter
	private String idPattern;
	private String closeEvent;//close keep(means debug,only run first one,can not run all match) screen

    public SeleniumBase(String inIdPattern,String inCloseEvent) {
        this.idPattern = inIdPattern;
        this.closeEvent = inCloseEvent;
    }
    @Parameters
    public static Collection<String[]> addParamter() {
        return Arrays.asList(new String[][] { { null, "close" } });
    }

	@Test
	public void testCaseArray() throws Exception {
		Gson gson = new Gson();

		String url = WebDriverUtil.getInstance().getAutomationConfig().getTestDataDic()+jsonFile+".json";
		//if ( jsonCase==null ) {
		JSONObject jsonCase = new JSONObject(FileUtil.getInstance().readFile(url));

		JSONArray caseArray = jsonCase.getJSONArray("caseArray");
		E2ECaseResult caseResult = null;
		List<E2ELoopResult> loopResultList;
		String fileId = e2EUtil.jsonString(jsonCase, "id");
		//tip delete this dic will in debug mode
		if ( resultId!=null ) {
			fileId = resultId;
		}
		String filePath = WebDriverUtil.getInstance().getAutomationConfig().getResultDic()+fileId+".res";
		try {
			String fileContent = null;
			fileContent = FileUtil.getInstance().readFile(filePath);

			caseResult = gson.fromJson(fileContent, E2ECaseResult.class);
		} catch ( Exception e ) {
			caseResult = null;
		}
		if ( caseResult==null ) {
			caseResult = new E2ECaseResult();
			caseResult.setDesc( e2EUtil.jsonString(jsonCase, "desc") );
			caseResult.setId( fileId );
		}

		loopResultList = caseResult.getLoopResultList();
		if ( loopResultList==null ) {
			loopResultList = new ArrayList<E2ELoopResult>();
			caseResult.setLoopResultList(loopResultList);
		}

		String field,value;
		String result;

		JSONObject jsonRow;
		E2ELoopResult loopResult;
		boolean foundPattern = false;
		
		String skipPositive = WebDriverUtil.getInstance().getAutomationConfig().getSkipPositive();
		String skipNegative = WebDriverUtil.getInstance().getAutomationConfig().getSkipNegative();
		String[] skipPositiveArray = new String[0];
		if ( skipPositive!=null && !skipPositive.equals("") ) {
			skipPositiveArray = skipPositive.split("&&&");
		}
		String[] skipNegativeArray = new String[0];
		if ( skipNegative!=null && !skipNegative.equals("") ) {
			skipNegativeArray = skipNegative.split("&&&");
		}
		System.out.println("set condition");
		
		for (int k = 0; k <1; k++) {
		for (int i = 0; i < caseArray.length(); i++)
		{
			jsonRow = caseArray.getJSONObject(i);

			loopResult = null;
			String rowId = e2EUtil.jsonString(jsonRow, "id");
			if ( rowId==null ) {
				rowId = "";
			}
			System.out.println("rowId:"+rowId);

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

			if ( "success".equals(loopResult.getResult()) && !"keep".equals(this.closeEvent) ) {
				continue;
			}
			/**/
			if ( "error".equals(loopResult.getResult()) && !"keep".equals(this.closeEvent) ) {
				String str = loopResult.getSummary();
				//TODO config
				//if ( str.indexOf("Status: Failed. Sorry, we are not able to proceed")>=0 || str.indexOf("Non-refundable service fee")>=0 || str.indexOf("Can not go to aaa")>=0 || str.indexOf("selenium check:Error Message Check fail!expect aaa")>=0 ) {
				if ( str.indexOf("There is not benefit for Women In Skilled Trades and Information Technology Training")>=0 || str.indexOf("There is not benefit for Microlending for Women in Ontario Program!")>=0 || str.indexOf("There is not benefit for Employment Training for Abused and At-Risk Women!")>=0 || str.indexOf("There is not benefit for Ontario Works!")>=0 ) {
				//if ( str.indexOf("Please Answer Q3a")<0 ) {
					//continue;
				}
				
				if ( skipPositiveArray.length>0 ) {
					if ( checkCondition(str,skipPositiveArray) ) {
						continue;
					}
				}
				if ( skipNegativeArray.length>0 ) {
					if ( checkCondition(str,skipNegativeArray) ) {
					} else {
						continue;
					}
				}
			}

			//check pattern, if success , not working
			if ( idPattern!=null ) {
				if ( rowId.startsWith(idPattern) ) {
					foundPattern = true;
					System.out.println("idPattern:"+idPattern+":"+rowId);
				} else {
					continue;
				}
			}

			field = "skip";
			value = e2EUtil.jsonString(jsonCase, field);
			if ( "true".equals(value) ) {
				loopResult.setResult("success");
				continue;
			}

			String summary = null;
			try {
				//if want to get half summary,code in child
				//throw new Exception(e.getMessage()+"("+summary+")",e);	

				summary = this.runCase(jsonRow);
				loopResult.setResult("success");
				loopResult.setSummary( DateUtil.getInstance().getCurrentTimeSummary()+summary );
			} catch ( CheckException e ) {
				summary = e.getMessage();
				loopResult.setResult("error");
				loopResult.setSummary( DateUtil.getInstance().getCurrentTimeSummary()+summary );
			} catch ( Exception e ) {
				e.printStackTrace();

				summary = e.getMessage();
				loopResult.setResult("error");
				loopResult.setSummary( DateUtil.getInstance().getCurrentTimeSummary()+summary );
			} finally {
				try {
					//can not no restart??  &&  foundPattern
					if ( "keep".equals(this.closeEvent) ) {
						break;// do not write result
					} else {
						this.restartRoot();
					}
					
					//TODO screen capture
					
					result = gson.toJson(caseResult);
					System.out.println("finish test data:"+e2EUtil.jsonString(jsonRow, "id")+":"+loopResult.getResult()+":"+loopResult.getSummary() );
					//System.out.println(result);
					this.writeFile(filePath, result);
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
			
		}}

		/*
		result = gson.toJson(caseResult);
		System.out.println(result);
		this.writeFile(filePath, result);*/
	}

	private boolean checkCondition(String msg, String[] conditionArray) {
		// TODO Auto-generated method stub
		for ( String condition:conditionArray) {
			if ( condition!=null && !condition.equals("") ) {
				if ( msg.indexOf(condition)>=0 ) {
					return true;
				}
			}
		}
		return false;
	}

	private void writeFile(String filePath,String fileContent) throws Exception {
		PrintWriter out = new PrintWriter(filePath);

		out.print(fileContent);

		out.close();
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

	protected void waitItShort() throws InterruptedException {
		if ( this.mode>=0 ) {
			Thread.sleep(500);
		}
	}
	
	//AutomationConfig automationConfig = createConfig();
	//WebDriverUtil.getInstance()
	protected String fixNameSpace(String id) {
		String nameSpace = WebDriverUtil.getInstance().getAutomationConfig().getNameSpace();
		return nameSpace+id;
	}

	protected void checkPageError() throws Exception {
		Thread.sleep(500);

		List<WebElement> elementList = driver.findElements(By.cssSelector(".Errors ul li"));
		if ( elementList.size()>0 ) {
			String summary = "";
			for( WebElement elementSingle : elementList ) {
				if ( elementSingle.getText()!=null && !elementSingle.getText().equals("") ) {
					summary += elementSingle.getText()+"<br/>";
				}
			}
			
			if ( !summary.equals("") ) {
				throw new CheckException(summary);
			}
		}

		elementList = driver.findElements(By.cssSelector(".Errors"));
		if ( elementList.size()>0 ) {
			//for iac single error
			for( WebElement elementSingle : elementList ) {
				if ( elementSingle.getText().indexOf("ErrorCode:")>=0 ) {
					throw new CheckException(elementSingle.getText());
				}
			}
		}
		//Errors
	}
}
