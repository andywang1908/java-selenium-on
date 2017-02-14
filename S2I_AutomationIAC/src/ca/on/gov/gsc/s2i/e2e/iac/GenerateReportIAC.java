package ca.on.gov.gsc.s2i.e2e.iac;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.GenerateReportUtil;

public final class GenerateReportIAC {	
	@Test
	public void testGenerateReport() throws Exception {
		String resultDic= PropertyIAC.getInstance().getProperty("s2i.iac.resultDic");
		String excelResult= PropertyIAC.getInstance().getProperty("s2i.iac.excelResult");
		GenerateReportUtil generateReportTool = new GenerateReportUtil(resultDic,excelResult);
		
		generateReportTool.parseResult();
	}

}
