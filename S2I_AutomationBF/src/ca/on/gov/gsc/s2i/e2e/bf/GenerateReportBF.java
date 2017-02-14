package ca.on.gov.gsc.s2i.e2e.bf;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.GenerateReportUtil;

public final class GenerateReportBF {	
	@Test
	public void testGenerateReport() throws Exception {
		String resultDic= PropertyBF.getInstance().getProperty("s2i.bf.resultDic");
		String excelResult= PropertyBF.getInstance().getProperty("s2i.bf.excelResult");
		GenerateReportUtil generateReportTool = new GenerateReportUtil(resultDic,excelResult);
		
		generateReportTool.parseResult();
	}

}
