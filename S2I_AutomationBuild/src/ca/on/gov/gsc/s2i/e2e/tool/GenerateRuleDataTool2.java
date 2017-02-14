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

public class GenerateRuleDataTool2 {
	private final String testDataDic = "C:/green/e2e/rule/result";
	//Service Ontario Business Rules 2015-09-28.xlsx
	private final String excelSource = "C:/green/e2e/rule/BF - Requirements - v0.98.xlsx";

	@Test
	public void testGenerateTestData() throws Exception {
		this.paresXlsxByPath(excelSource);
	}

	private final static GenerateRuleDataTool2 instance = new GenerateRuleDataTool2();
	public static GenerateRuleDataTool2 getInstance() {
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
		if ( "4.BusinessRules".equalsIgnoreCase(sheetNameShort) || "ProgramResultsBusinessRules".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseEligibility(sheet,new String[]{sheetName});
		}
	}
	
	//private String[] param = {"a","b","c"};
	
	private void parseEligibility(Sheet sheet, String[] targets) throws Exception {
		int j = 0;

		ScenarioRule scenarioRule = new ScenarioRule();
		ruleList = new ArrayList<ScenarioRule>();
		//TODO config adjust

		String value;
		
		CondiTestData a1 = new CondiTestData(9,11);
		CondiTestData a2 = new CondiTestData(12,16);
		CondiTestData a3 = new CondiTestData(17,21);
		CondiTestData a4 = new CondiTestData(22,23);
		CondiTestData a5 = new CondiTestData(24,31);
		CondiTestData a6 = new CondiTestData(32,35);
		CondiTestData a7 = new CondiTestData(36,38);
		CondiTestData a8 = new CondiTestData(39,43);
		CondiTestData a9 = new CondiTestData(44,50);
		CondiTestData a10 = new CondiTestData(51,53);
		CondiTestData a11 = new CondiTestData(54,59);
		CondiTestData a12 = new CondiTestData(60,64);
		CondiTestData a13 = new CondiTestData(65,66);
		CondiTestData a14 = new CondiTestData(67,68);
		CondiTestData a15 = new CondiTestData(69,74);
		
		String benefit = "";
		for(j=3;j<63;j++) {//63 adjust
			boolean foundX = false;
			String type = "conflict";//conflict positive
			if ( type.equals("positive") ) {
				a1.calPositive(sheet, j);
				a2.calPositive(sheet, j);
				a3.calPositive(sheet, j);
				a4.calPositive(sheet, j);
				a5.calPositive(sheet, j);
				a6.calPositive(sheet, j);
				a7.calPositive(sheet, j);
				a8.calPositive(sheet, j);
				a9.calPositive(sheet, j);
				a10.calPositive(sheet, j);
				a11.calPositive(sheet, j);
				a12.calPositive(sheet, j);
				a13.calPositive(sheet, j);
				a14.calPositive(sheet, j);
				a15.calPositive(sheet, j);
			} else if ( type.equals("conflict") ) {
				if ( a1.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a2.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a3.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a4.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a5.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a6.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a7.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a8.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a9.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a10.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a11.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a12.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a13.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a14.calConflict(sheet, j) ) {
					foundX = true;
				}
				if ( a15.calConflict(sheet, j) ) {
					foundX = true;
				}
			}
			List<String> option1 = a1.options;
			List<String> option2 = a2.options;
			List<String> option3 = a3.options;
			List<String> option4 = a4.options;
			List<String> option5 = a5.options;
			List<String> option6 = a6.options;
			List<String> option7 = a7.options;
			List<String> option8 = a8.options;
			List<String> option9 = a9.options;
			List<String> option10 = a10.options;
			List<String> option11 = a11.options;
			List<String> option12 = a12.options;
			List<String> option13 = a13.options;
			List<String> option14 = a14.options;
			List<String> option15 = a15.options;
			
			if ( type.equals("conflict") ) {
				if (foundX) {
				} else {
					//would not input 
					option1 = new ArrayList<String>();
				}
			}
			
			value = getCellString( sheet.getRow(6-1), j);
			if ( value!=null && !value.equals("") ) {
				benefit = value;
			}
			
			for (String opt1:option1) {
				for (String opt2:option2) {
					for (String opt3:option3) {
						for (String opt4:option4) {
							for (String opt5:option5) {
								for (String opt6:option6) {
									for (String opt7:option7) {
										for (String opt8:option8) {
											for (String opt9:option9) {
												for (String opt10:option10) {
													for (String opt11:option11) {
														for (String opt12:option12) {
															for (String opt13:option13) {
																for (String opt14:option14) {
																	for (String opt15:option15) {
																		System.out.println(opt1+"	"+opt2+"	"+opt3+"	"+opt4+"	"+opt5+"	"+opt6+"	"+opt7+"	"+opt8+"	"+opt9+"	"+opt10+"	"+opt11+"	"+opt12+"	"+opt13+"	"+opt14+"	"+opt15+"	"+benefit);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private String genCondition(Sheet sheet,int j,String tag, ScenarioRule scenarioRule){
        
        return "";
	}
	
    public static void main(String[] args) throws SQLException {
    }
    
    private class CondiTestData {
    	private CondiTestData(int from,int to) {
    		this.from = from;
    		this.to= to;
    	}
    	private int from;
    	private int to;
    	
    	private List<String> options;// = new ArrayList<String>();
        
    	private boolean calConflict(Sheet sheet,int j) {
    		options = new ArrayList<String>();

    		String value;
    		
    		for ( int i=from;i<=to;i++ ) {
	        	value = getCellString( sheet.getRow(i-1), j);
	        	
	        	if ( value!=null && value.equals("√") ) {
	        		options.add(""+(i-from+1));
	        	}
    		}

    		boolean foundX = false;
    		if ( options.size()==0 ) {
        		for ( int i=from;i<=to;i++ ) {
    	        	value = getCellString( sheet.getRow(i-1), j);
    	        	
    	        	if ( value!=null && value.equals("X") ) {
    	        		foundX = true;
    	        		break;
    	        	}
        		}
        		
        		if ( foundX ) {
            		for ( int i=from;i<=to;i++ ) {
        	        	value = getCellString( sheet.getRow(i-1), j);
        	        	
        	        	if ( value!=null && value.equals("X") ) {
        	        		options.add(""+(i-from+1));
        	        	}
            		}
        		} else {
            		for ( int i=from;i<=to;i++ ) {
        	        	value = getCellString( sheet.getRow(i-1), j);
        	        	
        	        	if ( value!=null && value.equals("X") ) {
        	        	} else {
        	        		options.add(""+(i-from+1));
        	        		break;
        	        	}
            		}
        		}
    		}
    		if ( options.size()==0 ) {
        		options.add("1");
    		}
    		
    		return foundX;
    	}
        
    	private List<String> calPositive(Sheet sheet,int j) {
    		options = new ArrayList<String>();

    		String value;
    		
    		for ( int i=from;i<=to;i++ ) {
	        	value = getCellString( sheet.getRow(i-1), j);
	        	
	        	if ( value!=null && value.equals("√") ) {
	        		options.add(""+(i-from+1));
	        	}
    		}
    		
    		if ( options.size()==0 ) {
        		for ( int i=from;i<=to;i++ ) {
    	        	value = getCellString( sheet.getRow(i-1), j);
    	        	
    	        	if ( value!=null && value.equals("X") ) {
    	        	} else {
    	        		options.add(""+(i-from+1));
    	        		break;
    	        	}
        		}
    		}
    		if ( options.size()==0 ) {
        		options.add("1");
    		}
    		
    		return options;
    	}
    }
    
}
