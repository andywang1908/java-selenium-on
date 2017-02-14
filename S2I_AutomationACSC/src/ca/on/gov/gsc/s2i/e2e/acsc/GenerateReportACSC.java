package ca.on.gov.gsc.s2i.e2e.acsc;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.GenerateReportUtil;

public final class GenerateReportACSC {	
	@Test
	public void testGenerateReport() throws Exception {
		String resultDic= PropertyACSC.getInstance().getProperty("s2i.acsc.resultDic");
		String excelResult= PropertyACSC.getInstance().getProperty("s2i.acsc.excelResult");
		GenerateReportUtil generateReportTool = new GenerateReportUtil(resultDic,excelResult);
		
		generateReportTool.parseResult();
	}
}
