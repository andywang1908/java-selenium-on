Setup
1.copy chromedriver.exe1(in this dictionary) to c:/green/e2e/chromedriver.exe
a.you can change path(c:/green/e2e/) in Project Properties(IACBase.properties) key s2i.iac.localDic;
b.please make sure change the suffix from exe1 to exe
c.also you can download the latest version in https://sites.google.com/a/chromium.org/chromedriver/downloads

2.copy clean.bat1(in this dictionary) to c:bgreenbclean.bat,this will clean chromedriver.exe automatically for you.otherwise you need clean it by yourself.
a.please make sure change the suffix from bat1 to bat

3.check project properties to map your local folders and your test URL.such as /S2I_AutomationIAC/src/ca/on/gov/gsc/s2i/e2e/iac/IACBase.properties

Run Scenario
1.put test data template in the folder s2i.iac.excelSource, such as C:/green/e2e/iac/IAC-Scenario.xlsx
2.run /S2I_AutomationIAC/src/ca/on/gov/gsc/s2i/e2e/iac/GenerateTestDataIAC.java as junit test,it will generate test data in the folder s2i.iac.testDataDic
3.run specific scenario /S2I_AutomationIAC/src/ca/on/gov/gsc/s2i/e2e/iac/scenario/SameAddressTest.java, it will test IAC Same Address Scenario base on the test data, C:/green/e2e/iac/result/Same Address.json
4.run /S2I_AutomationIAC/src/ca/on/gov/gsc/s2i/e2e/iac/GenerateReportIAC.java, it will generate test report which is html format in the folder s2i.iac.resultDic , and Excel format in the folder s2i.iac.excelResult
5.Once you complete the test round,run /S2I_AutomationIAC/src/ca/on/gov/gsc/s2i/e2e/iac/CleanTestResultIAC.java,it will clean the result which *.res in s2i.iac.resultDic for next round. 

PS:
1.you can set your test browser to Chrome FireFox IE, the key is s2i.iac.driverType.
2.if you only want to run the first test data, you can put the constant value mode=0.
