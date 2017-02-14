package ca.on.gov.gsc.s2i.e2e.bf;

import org.junit.Test;

import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class CleanTestResultBF {
	@Test
	public void testCleanTestResult() throws Exception {
		FileUtil.getInstance().cleanBackUpFolder(PropertyBF.getInstance().getProperty("s2i.bf.resultDic"), PropertyBF.getInstance().getProperty("s2i.bf.backUpDic"), ".res");
	}
}
