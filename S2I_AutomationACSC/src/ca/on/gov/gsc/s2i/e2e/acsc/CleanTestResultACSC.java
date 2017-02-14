package ca.on.gov.gsc.s2i.e2e.acsc;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class CleanTestResultACSC {
	@Test
	public void testCleanTestResult() throws Exception {
		FileUtil.getInstance().cleanBackUpFolder(PropertyACSC.getInstance().getProperty("s2i.acsc.resultDic"), PropertyACSC.getInstance().getProperty("s2i.acsc.backUpDic"), ".res");
	}
}
