package ca.on.gov.gsc.s2i.e2e.model;

public class AutomationConfig {
	private String resultDic;
	private String rootUrl;
	private String testDataDic;
	private String driverType;
	private String localDic;

	private int windowHeight;
	private int windowWidth;

	private String resultFile;
	private String testDataFile;
	
	private String nameSpace;

	private String skipPositive;
	private String skipNegative;
	
	
	public String getResultDic() {
		return resultDic;
	}
	public void setResultDic(String resultDic) {
		this.resultDic = resultDic;
	}
	public String getRootUrl() {
		return rootUrl;
	}
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	public String getTestDataDic() {
		return testDataDic;
	}
	public void setTestDataDic(String testDataDic) {
		this.testDataDic = testDataDic;
	}
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	public String getLocalDic() {
		return localDic;
	}
	public void setLocalDic(String localDic) {
		this.localDic = localDic;
	}
	public int getWindowHeight() {
		return windowHeight;
	}
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}
	public int getWindowWidth() {
		return windowWidth;
	}
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}
	public String getResultFile() {
		return resultFile;
	}
	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}
	public String getTestDataFile() {
		return testDataFile;
	}
	public void setTestDataFile(String testDataFile) {
		this.testDataFile = testDataFile;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getSkipPositive() {
		return skipPositive;
	}
	public void setSkipPositive(String skipPositive) {
		this.skipPositive = skipPositive;
	}
	
	public String getSkipNegative() {
		return skipNegative;
	}
	public void setSkipNegative(String skipNegative) {
		this.skipNegative = skipNegative;
	}
}
