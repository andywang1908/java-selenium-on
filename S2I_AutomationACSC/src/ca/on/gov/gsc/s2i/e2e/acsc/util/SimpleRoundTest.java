package ca.on.gov.gsc.s2i.e2e.acsc.util;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.acsc.GenerateTestDataACSC;
import ca.on.gov.gsc.s2i.e2e.acsc.PropertyACSC;
import ca.on.gov.gsc.s2i.e2e.acsc.scenario.IneligibilityTest;
import ca.on.gov.gsc.s2i.e2e.tool.GenerateTestDataTool;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;
import ca.on.gov.gsc.s2i.e2e.util.GenerateReportUtil;

public class SimpleRoundTest {

	public static void main(String[] args) throws Exception {
		SimpleRoundTest simpleRoundTest = new SimpleRoundTest();
		simpleRoundTest.testSimpleRound();
	}
	
	@Test
	public void testSimpleRound() throws Exception {
		String resultDic= PropertyACSC.getInstance().getProperty("s2i.acsc.resultDic");
		String excelResult= PropertyACSC.getInstance().getProperty("s2i.acsc.excelResult");
		String excelSource= PropertyACSC.getInstance().getProperty("s2i.acsc.excelSource");
		String backUpDic= PropertyACSC.getInstance().getProperty("s2i.acsc.backUpDic");
		
		int mode=0;//0 test 1 run
		
		//step1:copy
		//step2:gen test data
		if ( mode==1 ) {
			GenerateTestDataTool generateTestDataTool = new GenerateTestDataACSC();
			generateTestDataTool.paresXlsxByPath(excelSource);
		}
		
		//step3:run
		IneligibilityTest ineligibilityTest = new IneligibilityTest(null, "close");
		ineligibilityTest.setUp();
		ineligibilityTest.testCaseArray();
		//TODO run again in another test method without clean res
		ineligibilityTest.tearDown();
		//TODO run other scenario
		//TODO multi thread,so far easy to set each scenario for each thread, hard to set one scenario for multi thread

		//step4:gen report
		if ( mode==1 ) {
			GenerateReportUtil generateReportTool = new GenerateReportUtil(resultDic,excelResult);
			generateReportTool.parseResult();
		}

		//step5:clean  can not be first step,otherwise can not added old result
		if ( mode==1 ) {
			FileUtil.getInstance().cleanBackUpFolder(resultDic, backUpDic, ".res");
		}
		
		//TODO send mail
		//TODO schedule
	}
}
