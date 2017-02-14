package ca.on.gov.gsc.s2i.e2e.tool;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.ScenarioRule;

public class GenerateRuleDataTool {
	private final String testDataDic = "C:/green/e2e/rule/result";
	//Service Ontario Business Rules 2015-09-28.xlsx
	private final String excelSource = "C:/green/e2e/rule/GSIC Business Rules_v0.4 Updated 2015-10-29.xlsx";

	@Test
	public void testGenerateTestData() throws Exception {
		this.paresXlsxByPath(excelSource);
	}

	private final static GenerateRuleDataTool instance = new GenerateRuleDataTool();
	public static GenerateRuleDataTool getInstance() {
		return instance;
	}
	private List<ScenarioRule> ruleList = null;//new ArrayList<ScenarioRule>();
	public List<ScenarioRule> getRuleList() throws Exception {
		if ( ruleList==null ) {
			this.paresXlsxByPath(excelSource);
		}
		return ruleList;
	}
	
	protected String getCellString(Row row,int index) {
		String result = null;
		Cell cell = row.getCell(index);
		if ( cell!=null ) {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);//HSSFCell.CELL_TYPE_STRING
			result = cell.toString();
		}

		return result;
	}
	
	protected void paresXlsxByName(String fileName) throws Exception {
		String filePath = this.getClass().getResource(fileName).getPath();
		
		this.paresXlsxByPath(filePath);
	}
	
	public void paresXlsxByPath(String filePath) throws Exception {
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	
        Workbook wb = null;
        String sheetName;
        try {
        	wb = new XSSFWorkbook(fileInputStream);
        	
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            	sheetName = wb.getSheetName(i);
            	//TODO to doc
            	if ( !sheetName.toUpperCase().endsWith(" RESULT") ) {
            		//sheetName = sheetName.replaceAll(" ", "");
            		this.parseSheet(wb.getSheetAt(i),sheetName,new String[]{sheetName});
            	}
            }
        } catch ( Exception e ) {
        	throw e;
        } finally {
        	if ( wb!=null ) {
                wb.close();
        	}
        }
	}

	//protected abstract void parsePostCode(Sheet sheet,String[] targets) throws Exception;
	protected void parseSheet(Sheet sheet,String sheetName,String[] targets) throws Exception {
		String sheetNameShort = sheetName.replaceAll(" ", "");
		if ( "4.BusinessRules".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseEligibility(sheet,new String[]{sheetName});
		}
	}
	
	//private String[] param = {"a","b","c"};

	private void parseEligibility(Sheet sheet, String[] targets) throws Exception {
		int j = 0;

		ScenarioRule scenarioRule = new ScenarioRule();
		ruleList = new ArrayList<ScenarioRule>();
		//TODO config adjust
		for(j=3;j<63;j++) {//63 adjust
			scenarioRule.setRuleSuccess( this.genCondition(sheet, j, "√", scenarioRule) );
			scenarioRule.setRuleFail( this.genCondition(sheet, j, "X", scenarioRule) );
			
			ruleList.add(scenarioRule.clone());
		}

		/*
		j=3;
		for(ScenarioRule scenarioRuleSingle:ruleList) {
			System.out.println(scenarioRuleSingle);//j+":"+
			j++;
		}*/
		
		this.saveToDB(ruleList);
	}
	
	private void saveToDB(List<ScenarioRule> ruleList) throws SQLException {

    	Connection myConnection = null;
    	Statement sqlStatement = null;
        try { 
            String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=susracdev.service.cihs.gov.on.ca)(PORT=1525)))(CONNECT_DATA=(SERVICE_NAME=susdev)))";
            String strUserID = "s2i";
            String strPassword = "s2i";
            
            myConnection=DriverManager.getConnection(dbURL,strUserID,strPassword);
            sqlStatement = myConnection.createStatement();
            
            String readRecordSQL = "select max(rule_id)+1 as max from bf_rule";
            ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
            int max=1000;
            while (myResultSet.next()) {
                max = myResultSet.getInt("max");
                System.out.println("Record Max: " + max);
            }

            
    		for(ScenarioRule scenarioRuleSingle:ruleList) {
    			max++;
    			System.out.println(scenarioRuleSingle);
    			
    			if ( scenarioRuleSingle.getRuleSuccess()!=null && !scenarioRuleSingle.getRuleSuccess().equals("") ) {
        			readRecordSQL = "insert into bf_rule(rule_id,program_code,business_regex,exception_regex) values('"+max+"','"+scenarioRuleSingle.getProgram()+"','"+scenarioRuleSingle.getRuleSuccess()+"','"+scenarioRuleSingle.getRuleFail()+"')";
        			System.out.println(readRecordSQL);
        			sqlStatement.executeQuery(readRecordSQL);
    			}
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
		
	}
	
	private Map<String, String > mapQuestion = new HashMap<String, String>(){{
        put("Q1","AppAge");
        put("Q2","Income");
        put("Q3","Working");
        put("Q3a","Training");
        put("Q4","FamSize");
        put("Q5","Children");
        put("Q5a","ChildAge");
        put("Q5b","ChildHealth");
        put("Q6","AppHealth");
        put("Q7","Student");
        put("Q8","Housing");
        put("Q8a","HouseAssist");
        put("Q9","LegalAssist");
        put("Q10","FinAssistYN");
        put("Q10a","FinAssist");
    }};
	
	private Map<String, String > mapAnswer = new HashMap<String, String>(){{
		int i=9;//adjust
		String TagA = "A";

        put(TagA+i++,"AppAge_18");//9
        put(TagA+i++,"AppAge_18to64");
        put(TagA+i++,"AppAge_65");
        put(TagA+i++,"Income_A");
        put(TagA+i++,"Income_B");
        put(TagA+i++,"Income_C");
        put(TagA+i++,"Income_D");
        put(TagA+i++,"Income_E");
        put(TagA+i++,"Working_FT");
        put(TagA+i++,"Working_PT");
        put(TagA+i++,"Working_LW");
        put(TagA+i++,"Working_UE");
        put(TagA+i++,"Working_RW");
        put(TagA+i++,"Training_Yes");
        put(TagA+i++,"Training_No");
        put(TagA+i++,"FamSize_1");
        put(TagA+i++,"FamSize_2");
        put(TagA+i++,"FamSize_3");
        put(TagA+i++,"FamSize_4");
        put(TagA+i++,"FamSize_5");
        put(TagA+i++,"FamSize_6");
        put(TagA+i++,"FamSize_7");
        put(TagA+i++,"FamSize_8");
        put(TagA+i++,"Children_Dep");
        put(TagA+i++,"Children_Adopt");
        put(TagA+i++,"Children_TempCare");
        put(TagA+i++,"Children_None");
        put(TagA+i++,"ChildAge_12");
        put(TagA+i++,"ChildAge_12to17");
        put(TagA+i++,"ChildAge_18");
        put(TagA+i++,"ChildHealth_Disabled");
        put(TagA+i++,"ChildHealth_Chronic");
        put(TagA+i++,"ChildHealth_Mobility");
        put(TagA+i++,"ChildHealth_Drugs");
        put(TagA+i++,"ChildHealth_None");
        put(TagA+i++,"AppHealth_Disabled");
        put(TagA+i++,"AppHealth_Chronic");
        put(TagA+i++,"AppHealth_Mobility");
        put(TagA+i++,"AppHealth_Mental");
        put(TagA+i++,"AppHealth_Addictions");
        put(TagA+i++,"AppHealth_Drugs");
        put(TagA+i++,"AppHealth_None");
        put(TagA+i++,"Student_Yes");
        put(TagA+i++,"Student_Return");
        put(TagA+i++,"Student_No");
        put(TagA+i++,"Housing_Own");
        put(TagA+i++,"Housing_Rent");
        put(TagA+i++,"Housing_LTC");
        put(TagA+i++,"Housing_RHC");
        put(TagA+i++,"Housing_Homeless");
        put(TagA+i++,"Housing.None");
        put(TagA+i++,"HouseAssist_HP");
        put(TagA+i++,"HouseAssist_SH");
        put(TagA+i++,"HouseAssist_LTC");
        put(TagA+i++,"HouseAssist_MovingOut");
        put(TagA+i++,"HouseAssist_None");
        put(TagA+i++,"LegalAssist_Yes");
        put(TagA+i++,"LegalAssist_No");
        put(TagA+i++,"FinAssistYN_Yes");
        put(TagA+i++,"FinAssistYN_No");
        put(TagA+i++,"FinAssist_ACSD");
        put(TagA+i++,"FinAssist_ODSP");
        put(TagA+i++,"FinAssist_OW");
        put(TagA+i++,"FinAssist_SC");
        put(TagA+i++,"FinAssist_TCA");
        put(TagA+i++,"FinAssist_Other");
    }};

	
	private String genCondition(Sheet sheet,int j,String tag, ScenarioRule scenarioRule){
		String str;
		String questionName = "";
		String questionNamePreConditon = "";
		String conditon = "";
		String conditonLink = "";
		int questionIndex = 1;

		int i=1;
		
		String ministry;
		String program;
		String scenario;
		
        for (Row row : sheet) {
			i++;//nature not like j
			//System.out.println("line no:"+i+":"+getCellString(row,0));

			if ( i==5 ) {
				ministry = getCellString(row, j);
				if ( ministry!=null && !ministry.equals("") ) {
					scenarioRule.setMinistry(ministry);
				}
			}
			if ( i==6 ) {
				program = getCellString(row, j);
				if ( program!=null && !program.equals("") ) {
					scenarioRule.setProgram(program);
				}
			}
			if ( i==8 ) {
				scenario = getCellString(row, j);
				if ( scenario!=null && !scenario.equals("") ) {
					scenarioRule.setScenario(scenario);
				}
			}
			
			if ( i<9 || i>74 ) {//79 74//adjust
				continue;
			}

			if ( questionName.equals(getCellString(row,0)) || getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				//index++
				questionIndex++;
				//conditonLink = "&";
			} else {
				questionName = getCellString(row,0);
				questionIndex = 1;
				//conditonLink = "&&";
			}
										
			str = getCellString(row,j);

			if ( tag.equals(str) ) {
				if ( !questionNamePreConditon.equals(questionName) || "".equals(questionNamePreConditon) ) {
					if ( !"".equals(conditon) ) {
						conditon += ")){1}";
					}
					conditon += ".*"+"("+mapQuestion.get(questionName)+":("+mapAnswer.get("A"+i);	
				} else {
					conditon += "|"+mapAnswer.get("A"+i);
				}
				
				//System.out.println("j:"+j+":i:"+i+":"+questionName+":A"+questionIndex+""+":√");
				questionNamePreConditon = questionName;
			}
        }

        if ( !conditon.equals("") ) {
        	conditon += ")){1}"+".*";
        }
        
        return conditon;
	}
		
	/*
	private String genCondition1(Sheet sheet,int j,String tag, ScenarioRule scenarioRule){
		String str;
		String questionName = "";
		String questionNamePreConditon = "";
		String conditon = "";
		String conditonLink = "";
		int questionIndex = 1;

		int i=1;
		
		String ministry;
		String program;
		String scenario;
		
        for (Row row : sheet) {
			i++;//nature not like j
			//System.out.println("line no:"+i+":"+getCellString(row,0));

			if ( i==5 ) {
				ministry = getCellString(row, j);
				if ( ministry!=null && !ministry.equals("") ) {
					scenarioRule.setMinistry(ministry);
				}
			}
			if ( i==6 ) {
				program = getCellString(row, j);
				if ( program!=null && !program.equals("") ) {
					scenarioRule.setProgram(program);
				}
			}
			if ( i==8 ) {
				scenario = getCellString(row, j);
				if ( scenario!=null && !scenario.equals("") ) {
					scenarioRule.setScenario(scenario);
				}
			}
			
			if ( i<9 || i>79 ) {
				continue;
			}

			if ( questionName.equals(getCellString(row,0)) || getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				//index++
				questionIndex++;
				//conditonLink = "&";
			} else {
				questionName = getCellString(row,0);
				questionIndex = 1;
				//conditonLink = "&&";
			}
			
			if ( !questionNamePreConditon.equals(questionName) || "".equals(questionNamePreConditon) ) {
				conditonLink = "&&";
			} else {
				conditonLink = "&";
			}
							
			str = getCellString(row,j);
							
			if ( tag.equals(str) ) {
				//System.out.println("j:"+j+":i:"+i+":"+questionName+":A"+questionIndex+""+":√");
				questionNamePreConditon = questionName;
				if ( mapQuestion.get(questionName)!=null && mapAnswer.get("A"+i)!=null ) {
					conditon += conditonLink+mapQuestion.get(questionName)+":"+mapAnswer.get("A"+i);//questionIndex
				} else {
					conditon += conditonLink+questionName+":A"+i;//questionIndex
				}
			}
        }

        //System.out.println("conditonFail:"+conditonFail);
        //System.out.println("rule-id:"+scenarioRule.getMinistry()+"-"+scenarioRule.getProgram()+"-"+scenarioRule.getScenario());
        //System.out.println("conditon("+tag+"):"+conditon);
        
        if ( conditon.startsWith("&&") ) {
        	conditon = conditon.substring(2);
        }
        
        return conditon;
	}*/
	
    public static void main(String[] args) throws SQLException {
    	Connection myConnection = null;
    	Statement sqlStatement = null;
        try { 
            String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=susracdev.service.cihs.gov.on.ca)(PORT=1525)))(CONNECT_DATA=(SERVICE_NAME=susdev)))";
            String strUserID = "s2i";
            String strPassword = "s2i";
            
            myConnection=DriverManager.getConnection(dbURL,strUserID,strPassword);
            sqlStatement = myConnection.createStatement();
            
            String readRecordSQL = "select count(1) as max from s2i_xml_log";  
            ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
            int max=1000;
            while (myResultSet.next()) {
                max = myResultSet.getInt("max");
                System.out.println("Record Max: " + max);
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
    }
}
