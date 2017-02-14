package ca.on.gov.gsc.s2i.e2e.bf;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.bf.model.ConditionData;
import ca.on.gov.gsc.s2i.e2e.model.CommonTestCase;
import ca.on.gov.gsc.s2i.e2e.tool.GenerateTestDataTool;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class GenerateTestDataBF extends GenerateTestDataTool {
	private final String testDataDic = PropertyBF.getInstance().getProperty("s2i.bf.testDataDic");
	private final String excelSource = PropertyBF.getInstance().getProperty("s2i.bf.excelSource");

	@Test
	public void testGenerateTestData() throws Exception {
		//this.paresXlsxByPath("IAC-Scenario.xlsx");
		this.paresXlsxByPath(excelSource);
	}

	@Override
	protected void parseSheet(Sheet sheet,String sheetName,String[] targets) throws Exception {
		String sheetNameShort = sheetName.replaceAll(" ", "");
		if ( "Sheet1".equalsIgnoreCase(sheetNameShort) || "BFBusinessRulesTestSet".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseSheet1(sheet,new String[]{sheetName});
		} else if ( "Conflict".equalsIgnoreCase(sheetNameShort) || "TestSetNegative".equalsIgnoreCase(sheetNameShort) || "Negative".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseSheet1(sheet,new String[]{sheetName});
		}
	}
	
	/*
	private int dealPostCode(PostCodeModel postCodeModel,Row row,int j) {
		postCodeModel.setQ3i1( getCellString(row,j++) );
		postCodeModel.setAddresses( getCellString(row,j++) );//name -- id
		postCodeModel.setRdoReAddressType( getCellString(row,j++) );//name -- id

		return j;
	}*/
	
	/**/
	private void parseSheet1(Sheet sheet,String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<ConditionData> caseArray = new ArrayList<ConditionData>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;//BF has first special column need to ignore
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			ConditionData testData = new ConditionData();
			caseArray.add( testData );

			j++;//for the extra first column in new template
			testData.setId( getCellString(row,j++) );
			testData.setDesc( "" );

			testData.setQ1( getCellString(row,j++) );
			testData.setQ2( getCellString(row,j++) );
			testData.setQ3( getCellString(row,j++) );
			testData.setQ4( getCellString(row,j++) );
			testData.setQ5( getCellString(row,j++) );
			testData.setQ6( getCellString(row,j++) );
			testData.setQ7( getCellString(row,j++) );
			testData.setQ8( getCellString(row,j++) );
			testData.setQ9( getCellString(row,j++) );
			testData.setQ10( getCellString(row,j++) );
			testData.setQ11( getCellString(row,j++) );
			testData.setQ12( getCellString(row,j++) );
			testData.setQ13( getCellString(row,j++) );
			testData.setQ14( getCellString(row,j++) );
			testData.setQ15( getCellString(row,j++) );
			testData.setQ16( getCellString(row,j++) );
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}
	
}
