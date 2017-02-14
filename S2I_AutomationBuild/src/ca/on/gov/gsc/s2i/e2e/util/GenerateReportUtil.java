package ca.on.gov.gsc.s2i.e2e.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import ca.on.gov.gsc.s2i.e2e.model.E2ECaseResult;
import ca.on.gov.gsc.s2i.e2e.model.E2ELoopResult;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class GenerateReportUtil {
	private String resultPath = null;//"c:/green"
	private String xlsxPath = null;//"c:/green/IAC-Sen.xlsx"
	private String ftlPath = "ca/on/gov/gsc/s2i/e2e/tool/ParseResult.ftl";
	
	public GenerateReportUtil(String p1,String p2) {
		this.resultPath = p1;
		this.xlsxPath = p2;
	}

	public void parseResult() throws Exception {
		this.parseResultByPath(getResultPath());
	}

	private void parseResultByPath(String resultDic) throws Exception {
		List<String> pathList = FileUtil.getInstance().listFolder(resultDic, ".res");
		//pathList.add(resultDic+"PostCode.res");
		
		this.parseResultByPathArray(pathList);
	}
	
	private void parseResultByPathArray(List<String> pathList) throws Exception {
		String fileContent,fileId;
		E2ECaseResult caseResult = null;
		Gson gson = new Gson();
		for (String filePath:pathList) {
			try {
				int i = filePath.lastIndexOf("/");
				int j = filePath.lastIndexOf(".res");
				fileId = filePath.substring(i+1,j);
				
				fileContent = FileUtil.getInstance().readFile(filePath);
				caseResult = gson.fromJson(fileContent, E2ECaseResult.class);

				this.parseSingleToHtml(caseResult,fileId);
				this.parseSingleToXlsx(caseResult,fileId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void parseSingleToHtml(E2ECaseResult caseResult,String fileId) throws Exception {
		List<E2ELoopResult> loopResultList;
		loopResultList = caseResult.getLoopResultList();

		//freemaker
		//Freemarker configuration object
        Configuration cfg = new Configuration();

		//String url = this.getClass().getResource("ParseResult.ftl").getPath();
		cfg.setClassForTemplateLoading(this.getClass(), "/");

        //Load template from source folder
        Template template = cfg.getTemplate(getFtlPath());

        //Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("caseResult", caseResult);
        data.put("loopResultList", loopResultList);
		int successCount = 0;
		int errorCount = 0;
		for (E2ELoopResult loopResult :loopResultList ) {
			if ( "success".equals(loopResult.getResult()) ) {
				successCount++;
				//loopResult.setWarnSign("");
			} else {
				errorCount++;
			}
		}
        data.put("successCount", successCount);
        data.put("errorCount", errorCount);

        // File output
        File fileTarget = new File(getResultPath()+fileId+".html");
        Writer file = new FileWriter(fileTarget);
        template.process(data, file);
        file.flush();
        file.close();
	}

	private void parseSingleToXlsx(E2ECaseResult caseResult,String fileId) throws Exception {
		List<E2ELoopResult> loopResultList;
		loopResultList = caseResult.getLoopResultList();
		
		if ( getXlsxPath()==null ) return;
		
    	FileInputStream fileInputStream = new FileInputStream(getXlsxPath());
        Workbook wb = new XSSFWorkbook(fileInputStream);
        Sheet sheet = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
        	if ( (fileId+" Result").equalsIgnoreCase(wb.getSheetName(i)) ) {
        		sheet = wb.getSheetAt(i);
        		this.cleanSheetContent(sheet,5);
        		break;
        	}
        }
        if ( sheet==null ) {
            sheet = wb.createSheet(fileId+" Result");
        }
        //https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xssf/usermodel/examples/CreateCell.java
        //CreationHelper creationHelper = wb.getCreationHelper();

		int successCount = 0;
		int errorCount = 0;
		Row row;
		Cell cell;
		String str;
		CellStyle cs;
		int i=4,j;

		for (E2ELoopResult loopResult :loopResultList ) {
			row = sheet.createRow(i++);j=0;
			cell = row.createCell(j++);
			cell.setCellValue(loopResult.getId());
			cell = row.createCell(j++);
			cell.setCellValue(loopResult.getDesc());
			cell = row.createCell(j++);
			cell.setCellValue(loopResult.getResult());
			cell = row.createCell(j++);
			cs = wb.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			str = loopResult.getSummary();
			if ( str!=null ) {
				str = str.replaceAll("<br/>", "\n");
				if ( str.endsWith("\n") ) {
					str = str.substring(0,str.length()-1);
				}
			}
			cell.setCellValue(str);

			if ( "success".equals(loopResult.getResult()) ) {
				successCount++;
				//loopResult.setWarnSign("");
			} else {
				errorCount++;
			}
		}

        //deal header
		i=0;
		row = sheet.createRow(i++);j=0;
		cell = row.createCell(j++);
		cell.setCellValue("case id");
		cell = row.createCell(j++);
		cell.setCellValue(fileId);
		row = sheet.createRow(i++);j=0;
		cell = row.createCell(j++);
		cell.setCellValue("case desc");
		cell = row.createCell(j++);
		cell.setCellValue(fileId);
		row = sheet.createRow(i++);j=0;
		cell = row.createCell(j++);
		cell.setCellValue("success number");
		cell = row.createCell(j++);
		cell.setCellValue(successCount);
		row = sheet.createRow(i++);j=0;
		cell = row.createCell(j++);
		cell.setCellValue("error number");
		cell = row.createCell(j++);
		cell.setCellValue(errorCount);

        // File output
		FileOutputStream fileOut = new FileOutputStream(getXlsxPath());
        wb.write(fileOut);
        fileOut.close();
	}

	private void cleanSheetContent(Sheet sheet,int start) {
		int i=0;
        for (Row row : sheet) {
        	i++;
        	if (i<start) {
        		continue;
        	}

            for (Cell cell : row) {
                //break;
                cell.setCellValue("");
            }
        }
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getXlsxPath() {
		return xlsxPath;
	}

	public void setXlsxPath(String xlsxPath) {
		this.xlsxPath = xlsxPath;
	}

	public String getFtlPath() {
		return ftlPath;
	}

	public void setFtlPath(String ftlPath) {
		this.ftlPath = ftlPath;
	}

}
