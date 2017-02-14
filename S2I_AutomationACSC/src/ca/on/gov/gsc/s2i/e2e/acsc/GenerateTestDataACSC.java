package ca.on.gov.gsc.s2i.e2e.acsc;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.acsc.model.ChildModel;
import ca.on.gov.gsc.s2i.e2e.acsc.model.EligibilityData;
import ca.on.gov.gsc.s2i.e2e.acsc.model.EligibilityModel;
import ca.on.gov.gsc.s2i.e2e.acsc.model.FeeModel;
import ca.on.gov.gsc.s2i.e2e.acsc.model.FeeWaiverData;
import ca.on.gov.gsc.s2i.e2e.acsc.model.Parent1Data;
import ca.on.gov.gsc.s2i.e2e.acsc.model.Parent2PayerData;
import ca.on.gov.gsc.s2i.e2e.acsc.model.Parent2RecipientData;
import ca.on.gov.gsc.s2i.e2e.acsc.model.ParentModel;
import ca.on.gov.gsc.s2i.e2e.model.CommonTestCase;
import ca.on.gov.gsc.s2i.e2e.tool.GenerateTestDataTool;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class GenerateTestDataACSC extends GenerateTestDataTool {
	private final String testDataDic = PropertyACSC.getInstance().getProperty("s2i.acsc.testDataDic");
	private final String excelSource = PropertyACSC.getInstance().getProperty("s2i.acsc.excelSource");

	@Test
	public void testGenerateTestData() throws Exception {
		this.paresXlsxByPath(excelSource);
	}

	@Override
	protected void parseSheet(Sheet sheet,String sheetName,String[] targets) throws Exception {
		String sheetNameShort = sheetName.replaceAll(" ", "");
		if ( "Eligibility".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseEligibility(sheet,new String[]{sheetName});
		} else if ( "Ineligibility".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseEligibility(sheet,new String[]{sheetName});
		} else if ( "FeeWaiver".equalsIgnoreCase(sheetNameShort) || "FeeWaiverTestData".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseFeeWaiver(sheet,new String[]{"Fee Waiver"});
		} else if ( "Parent1".equalsIgnoreCase(sheetNameShort) || "Payment".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseParent1(sheet,new String[]{sheetName});
		} else if ( "Parent2Recipient".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseParent2Recipient(sheet,new String[]{sheetName});
		} else if ( "Parent2Payer".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseParent2Payer(sheet,new String[]{sheetName});
		}
	}

	private void parseFeeWaiver(Sheet sheet, String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<FeeWaiverData> caseArray = new ArrayList<FeeWaiverData>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			//TODO break or continue
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			FeeWaiverData testData = new FeeWaiverData();
			caseArray.add( testData );
			EligibilityModel model = new EligibilityModel();
			testData.setEligibility(model);
			FeeModel fee = new FeeModel();
			testData.setFee(fee);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealEligibility(model,row,j);
			//j--;
			j = this.dealFee(fee,row,j);
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}

	private void parseParent1(Sheet sheet, String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<Parent1Data> caseArray = new ArrayList<Parent1Data>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			//TODO break or continue
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			Parent1Data testData = new Parent1Data();
			caseArray.add( testData );
			EligibilityModel model = new EligibilityModel();
			testData.setEligibility(model);
			FeeModel fee = new FeeModel();
			testData.setFee(fee);
			ParentModel parent = new ParentModel();
			testData.setParent(parent);
			List<ChildModel> childList = new ArrayList<ChildModel>();
			testData.setChildList(childList);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealEligibility(model,row,j);
			//j--;
			j = this.dealParent(parent,row,j);
			j = this.dealChildList(childList,row,j);
			//j++;//additional sin       if the number is not empty,then must input
			testData.setAdditionalSin( getCellString(row,j++) );
			j = this.dealFee(fee,row,j);

			testData.setPaymentType( getCellString(row,j++) );
			testData.setPaymentAmount( getCellString(row,j++) );
			testData.setDownloadConsent( getCellString(row,j++) );
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}

	private void parseParent2Recipient(Sheet sheet, String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<Parent2RecipientData> caseArray = new ArrayList<Parent2RecipientData>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			//TODO break or continue
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			Parent2RecipientData testData = new Parent2RecipientData();
			caseArray.add( testData );
			FeeModel fee = new FeeModel();
			testData.setFee(fee);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );
			
			testData.setIdentificationNumber( getCellString(row,j++) );
			testData.setProceed( getCellString(row,j++) );
			testData.setOptReason( getCellString(row,j++) );
			testData.setTaxIsFiled( getCellString(row,j++) );
			
			testData.setSin( getCellString(row,j++) );
			testData.setBirthday( getCellString(row,j++) );
			testData.setEmail( getCellString(row,j++) );
			testData.setLanguage( getCellString(row,j++) );
			testData.setBenefit( getCellString(row,j++) );
			testData.setBenefitAmt( getCellString(row,j++) );
			
			j = this.dealFee(fee,row,j);

			testData.setPayType( getCellString(row,j++) );
			testData.setPayNo( getCellString(row,j++) );
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}

	private void parseParent2Payer(Sheet sheet, String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<Parent2PayerData> caseArray = new ArrayList<Parent2PayerData>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			//TODO break or continue
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			Parent2PayerData testData = new Parent2PayerData();
			caseArray.add( testData );
			FeeModel fee = new FeeModel();
			testData.setFee(fee);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );
			
			testData.setIdentificationNumber( getCellString(row,j++) );
			testData.setProofIncome( getCellString(row,j++) );//sp
			testData.setProceed( getCellString(row,j++) );
			testData.setOptReason( getCellString(row,j++) );
			testData.setComplexIncome( getCellString(row,j++) );//sp
			testData.setTaxIsFiled( getCellString(row,j++) );
			testData.setIncomeIsSeasonal( getCellString(row,j++) );//sp
			
			testData.setSin( getCellString(row,j++) );
			testData.setBirthday( getCellString(row,j++) );
			testData.setEmail( getCellString(row,j++) );
			testData.setLanguage( getCellString(row,j++) );
			testData.setBenefit( getCellString(row,j++) );
			testData.setBenefitAmt( getCellString(row,j++) );
			
			j = this.dealFee(fee,row,j);

			testData.setPayType( getCellString(row,j++) );
			testData.setPayNo( getCellString(row,j++) );
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}

	private void parseEligibility(Sheet sheet, String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<EligibilityData> caseArray = new ArrayList<EligibilityData>();
		testCase.setCaseArray(caseArray);

		int i = 0;
		int j = 0;
        for (Row row : sheet) {
			i++;
			j=0;

			if ( i==1 ) {
				continue;
			}

			//deal with blank row
			//TODO break or continue
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			EligibilityData testData = new EligibilityData();
			caseArray.add( testData );
			EligibilityModel model = new EligibilityModel();
			testData.setEligibility(model);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealEligibility(model,row,j);
        }

		Gson gson = new Gson();
		String result;
		for ( String target: targets ) {
			testCase.setId(target);
			testCase.setDesc(target);
			result = gson.toJson(testCase);

			String filePath = testDataDic+target+".json";
			FileUtil.getInstance().writeFile(filePath,result);
		}
	}

	private int dealEligibility(EligibilityModel model, Row row, int j) {
		model.setIsRecipient( getCellString(row,j++) );
		model.setDocType( getCellString(row,j++) );
		model.setIncAmt( getCellString(row,j++) );
		model.setComplexIncome( getCellString(row,j++) );
		model.setLiveInOntario( getCellString(row,j++) );
		model.setChildInAge( getCellString(row,j++) );
		model.setChildPer( getCellString(row,j++) );
		model.setAgreeDate( getCellString(row,j++) );
		model.setProvince( getCellString(row,j++) );
		model.setTaxIsFiled( getCellString(row,j++) );
		model.setProofIncome( getCellString(row,j++) );
		model.setIncomeIsSeasonal( getCellString(row,j++) );
		model.setMessage( getCellString(row,j++) );
		
		return j;
	}

	private int dealParent(ParentModel model, Row row, int j) {		
		model.setFirstName( getCellString(row,j++) );
		model.setMiddleName( getCellString(row,j++) );
		model.setLastName( getCellString(row,j++) );
		model.setDob( getCellString(row,j++) );
		model.setSin( getCellString(row,j++) );
		model.setPhone( getCellString(row,j++) );
		model.setEmail( getCellString(row,j++) );
		model.setPreferedLanguage( getCellString(row,j++) );

		model.setNameEmployer( getCellString(row,j++) );
		model.setContactNameEmployer( getCellString(row,j++) );
		model.setContactPhoneEmployer( getCellString(row,j++) );
		
		model.setFirstNameOther( getCellString(row,j++) );
		model.setMiddleNameOther( getCellString(row,j++) );
		model.setLastNameOther( getCellString(row,j++) );
		model.setEmailOther( getCellString(row,j++) );
		model.setPhoneOther( getCellString(row,j++) );
		model.setPreferedLanguageOther( getCellString(row,j++) );
		
		model.setFroNumber( getCellString(row,j++) );
		model.setSupportPayPeriod( getCellString(row,j++) );
		model.setAgreeFileNo( getCellString(row,j++) );
		model.setFileNumber( getCellString(row,j++) );
		model.setNorcID( getCellString(row,j++) );
		model.setJustice( getCellString(row,j++) );
		model.setCity( getCellString(row,j++) );
		
		//added
		model.setSupordLevel( getCellString(row,j++) );
		model.setSupordUnderDivorceAct( getCellString(row,j++) );
		
		model.setBeforeCourt( getCellString(row,j++) );
		model.setReceiveUCCB( getCellString(row,j++) );
		model.setUccbAmount( getCellString(row,j++) );
		//model.setSpousalPayerNo( getCellString(row,j++) );
		model.setIsSpousalPayer( getCellString(row,j++) );
		model.setSpousalPayerAmount( getCellString(row,j++) );
		model.setIsSpousalReceiver( getCellString(row,j++) );
		model.setSpousalReceiverAmount( getCellString(row,j++) );
		
		return j;
	}


	private int dealChildList(List<ChildModel> childList, Row row, int j) {
		ChildModel model;
		model = new ChildModel();
		childList.add( model );
		model.setFirstName( getCellString(row,j++) );
		model.setMiddleName( getCellString(row,j++) );
		model.setLastName( getCellString(row,j++) );
		model.setBirthday( getCellString(row,j++) );
		model.setFee1( getCellString(row,j++) );
		model.setFee2( getCellString(row,j++) );
		model.setFee3( getCellString(row,j++) );
		model.setFee4( getCellString(row,j++) );
		model.setFee5( getCellString(row,j++) );
		model.setNewFee1( getCellString(row,j++) );
		model.setNewFee2( getCellString(row,j++) );
		model.setNewFee3( getCellString(row,j++) );
		
		return j;
	}

	private int dealFee(FeeModel model, Row row, int j) {
		model.setSkip( getCellString(row,j++) );
		model.setPrimary( getCellString(row,j++) );
		model.setLiquid( getCellString(row,j++) );
		model.setNetworth( getCellString(row,j++) );
		model.setNumOfInHouse( getCellString(row,j++) );
		model.setGrossRange( getCellString(row,j++) );
		model.setExpectedFee( getCellString(row,j++) );
		
		return j;
	}
	
}
