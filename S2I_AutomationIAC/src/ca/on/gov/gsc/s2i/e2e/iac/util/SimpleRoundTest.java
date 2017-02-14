package ca.on.gov.gsc.s2i.e2e.iac.util;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.iac.GenerateTestDataIAC;
import ca.on.gov.gsc.s2i.e2e.iac.PropertyIAC;
import ca.on.gov.gsc.s2i.e2e.iac.scenario.SameAddressTest;
import ca.on.gov.gsc.s2i.e2e.tool.GenerateTestDataTool;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;
import ca.on.gov.gsc.s2i.e2e.util.GenerateReportUtil;

public class SimpleRoundTest {
	public static void main(String[] args) throws Exception {
		SimpleRoundTest simpleRoundTest = new SimpleRoundTest();
		simpleRoundTest.testSimpleRound();
	}
	
	//TODO run as junit
	@Test
	public void testSimpleRound() throws Exception {
		String resultDic= PropertyIAC.getInstance().getProperty("s2i.iac.resultDic");
		String excelResult= PropertyIAC.getInstance().getProperty("s2i.iac.excelResult");
		String excelSource= PropertyIAC.getInstance().getProperty("s2i.iac.excelSource");
		String backUpDic= PropertyIAC.getInstance().getProperty("s2i.iac.backUpDic");
		
		int mode=1;//0 test 1 run
		
		//step1:copy
		//step2:gen test data
		if ( mode==1 ) {
			GenerateTestDataTool generateTestDataTool = new GenerateTestDataIAC();
			generateTestDataTool.paresXlsxByPath(excelSource);
		}
		
		//step3:run
		SameAddressTest sameAddressTest = new SameAddressTest(null, "close");
		sameAddressTest.setUp();
		sameAddressTest.testCaseArray();
		//TODO run again in another test method without clean res
		sameAddressTest.tearDown();
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
