package ca.on.gov.gsc.s2i.e2e.iac;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.iac.model.CardModel;
import ca.on.gov.gsc.s2i.e2e.iac.model.ContactModel;
import ca.on.gov.gsc.s2i.e2e.iac.model.DifferAddressData;
import ca.on.gov.gsc.s2i.e2e.iac.model.PostCodeCheckData;
import ca.on.gov.gsc.s2i.e2e.iac.model.PostCodeModel;
import ca.on.gov.gsc.s2i.e2e.model.CommonTestCase;
import ca.on.gov.gsc.s2i.e2e.tool.GenerateTestDataTool;
import ca.on.gov.gsc.s2i.e2e.util.FileUtil;

public class GenerateTestDataIAC extends GenerateTestDataTool {
	private final String testDataDic = PropertyIAC.getInstance().getProperty("s2i.iac.testDataDic");
	private final String excelSource = PropertyIAC.getInstance().getProperty("s2i.iac.excelSource");

	@Test
	public void testGenerateTestData() throws Exception {
		//this.paresXlsxByPath("IAC-Scenario.xlsx");
		this.paresXlsxByPath(excelSource);
	}

	@Override
	protected void parseSheet(Sheet sheet,String sheetName,String[] targets) throws Exception {
		String sheetNameShort = sheetName.replaceAll(" ", "");
		if ( "PostalCodeCheck".equalsIgnoreCase(sheetNameShort) ) {
    		this.parsePostCode(sheet,new String[]{sheetName});
		} else if ( "PostalCodeMailing".equalsIgnoreCase(sheetNameShort) ) {
    		this.parsePostCode(sheet,new String[]{sheetName});
		} else if ( "SameAddress".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseSameAddress(sheet,new String[]{sheetName});
		} else if ( "DifferentAddress".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseDifferAddress(sheet,new String[]{sheetName});
		} else if ( "UATonlyMOH".equalsIgnoreCase(sheetNameShort) ) {
    		this.parseDifferAddressUAT(sheet,new String[]{sheetName});
		}
	}
	
	private int dealPostCode(PostCodeModel postCodeModel,Row row,int j) {
		postCodeModel.setQ3i1( getCellString(row,j++) );
		postCodeModel.setAddresses( getCellString(row,j++) );//name -- id
		postCodeModel.setRdoReAddressType( getCellString(row,j++) );//name -- id

		postCodeModel.setLotType( getCellString(row,j++) );
		postCodeModel.setLotNumber( getCellString(row,j++) );
		postCodeModel.setConcessionNumber( getCellString(row,j++) );
		postCodeModel.setPlanNumber( getCellString(row,j++) );
		postCodeModel.setTownship( getCellString(row,j++) );
		postCodeModel.setUnitType( getCellString(row,j++) );
		postCodeModel.setUnitNumber( getCellString(row,j++) );
		postCodeModel.setStreetNumber( getCellString(row,j++) );
		postCodeModel.setStreetNumSuffix( getCellString(row,j++) );
		postCodeModel.setStreetName( getCellString(row,j++) );
		postCodeModel.setStreetType( getCellString(row,j++) );
		postCodeModel.setStreetDirection( getCellString(row,j++) );
		postCodeModel.setDeliveryMode( getCellString(row,j++) );
		postCodeModel.setPoBoxNumber( getCellString(row,j++) );
		postCodeModel.setRouteType( getCellString(row,j++) );
		postCodeModel.setRouteNumber( getCellString(row,j++) );
		postCodeModel.setDeliveryType( getCellString(row,j++) );
		postCodeModel.setDeliveryQualName( getCellString(row,j++) );
		postCodeModel.setDeliveryAreaName( getCellString(row,j++) );
		postCodeModel.setMunicipality( getCellString(row,j++) );
		postCodeModel.setProvince( getCellString(row,j++) );
		postCodeModel.setPostalCode( getCellString(row,j++) );
		
		return j;
	}
	
	private int dealCard(CardModel card, Row row, int j) {
		card.setHealthCardType( getCellString(row,j++) );
		card.setHealthCardNumber( getCellString(row,j++) );
		card.setHealthCardVersionCode( getCellString(row,j++) );
		card.setLastPostalCodeMOH( getCellString(row,j++) );
		card.setLastFourDigit( getCellString(row,j++) );
		
		return j;
	}

	private void parseDifferAddressUAT(Sheet sheet,String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<DifferAddressData> caseArray = new ArrayList<DifferAddressData>();
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
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			DifferAddressData testData = new DifferAddressData();
			caseArray.add( testData );
			CardModel card = new CardModel();
			testData.setCard(card);
			PostCodeModel postCodeResidential = new PostCodeModel();
			testData.setPostCodeResidential(postCodeResidential);
			PostCodeModel postCodeMailing = new PostCodeModel();
			testData.setPostCodeMailing(postCodeMailing);
			ContactModel contact = new ContactModel();
			testData.setContact(contact);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealCard(card,row,j);
			j++;
			j = this.dealPostCode(postCodeResidential,row,j);
			j = this.dealPostCode(postCodeMailing,row,j);
			j = this.dealContact(contact,row,j);
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
		
	private void parseDifferAddress(Sheet sheet,String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<DifferAddressData> caseArray = new ArrayList<DifferAddressData>();
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
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			DifferAddressData testData = new DifferAddressData();
			caseArray.add( testData );
			CardModel card = new CardModel();
			testData.setCard(card);
			PostCodeModel postCodeResidential = new PostCodeModel();
			testData.setPostCodeResidential(postCodeResidential);
			PostCodeModel postCodeMailing = new PostCodeModel();
			testData.setPostCodeMailing(postCodeMailing);
			ContactModel contact = new ContactModel();
			testData.setContact(contact);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealCard(card,row,j);
			j = this.dealPostCode(postCodeResidential,row,j);
			j = this.dealPostCode(postCodeMailing,row,j);
			j = this.dealContact(contact,row,j);
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
	
	private void parseSameAddress(Sheet sheet,String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<DifferAddressData> caseArray = new ArrayList<DifferAddressData>();
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
			if ( getCellString(row,0)==null || getCellString(row,0).equals("") ) {
				break;
			}

			DifferAddressData testData = new DifferAddressData();
			caseArray.add( testData );
			CardModel card = new CardModel();
			testData.setCard(card);
			PostCodeModel postCodeResidential = new PostCodeModel();
			testData.setPostCodeResidential(postCodeResidential);
			//PostCodeModel postCodeMailing = new PostCodeModel();
			//testData.setPostCodeMailing(postCodeMailing);
			ContactModel contact = new ContactModel();
			testData.setContact(contact);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealCard(card,row,j);
			j = this.dealPostCode(postCodeResidential,row,j);
			j = this.dealContact(contact,row,j);
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
	
	private int dealContact(ContactModel contact, Row row, int j) {
		contact.setFirstName( getCellString(row,j++) );
		contact.setLastName( getCellString(row,j++) );
		contact.setHomephone( getCellString(row,j++) );
		contact.setPhone( getCellString(row,j++) );
		contact.setExtension( getCellString(row,j++) );
		contact.setEmail( getCellString(row,j++) );
		contact.setEmailConfirmation( getCellString(row,j++) );
		
		return j;
	}

	private void parsePostCode(Sheet sheet,String[] targets) throws Exception {
		CommonTestCase testCase = new CommonTestCase();
		
		List<PostCodeCheckData> caseArray = new ArrayList<PostCodeCheckData>();
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

			PostCodeCheckData testData = new PostCodeCheckData();
			caseArray.add( testData );
			PostCodeModel postCodeResidential = new PostCodeModel();
			testData.setPostCodeResidential(postCodeResidential);

			testData.setId( getCellString(row,j++) );
			testData.setDesc( getCellString(row,j++) );

			j = this.dealPostCode(postCodeResidential,row,j);
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
}
