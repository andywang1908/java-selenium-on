package ca.on.gov.gsc.s2i.e2e.iac;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class CleanTestResultIAC {
	@Test
	public void testCleanTestResult() throws Exception {
		FileUtil.getInstance().cleanBackUpFolder(PropertyIAC.getInstance().getProperty("s2i.iac.resultDic"), PropertyIAC.getInstance().getProperty("s2i.iac.backUpDic"), ".res");
	}
}
