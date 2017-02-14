package ca.on.gov.gsc.s2i.e2e.tool;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class GenerateTestDataTool {
	protected String getCellString(Row row,int index) {
		String result = null;
		Cell cell = row.getCell(index);
		if ( cell!=null ) {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);//HSSFCell.CELL_TYPE_STRING
			result = cell.toString();
		}

		return result;
	}
	
	protected void paresXlsxByName(String fileName) throws Exception {
		String filePath = this.getClass().getResource(fileName).getPath();
		
		this.paresXlsxByPath(filePath);
	}
	
	public void paresXlsxByPath(String filePath) throws Exception {
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	
        Workbook wb = null;
        String sheetName;
        try {
        	wb = new XSSFWorkbook(fileInputStream);
        	
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            	sheetName = wb.getSheetName(i);
            	//TODO to doc
            	if ( !sheetName.toUpperCase().endsWith(" RESULT") ) {
            		//sheetName = sheetName.replaceAll(" ", "");
            		this.parseSheet(wb.getSheetAt(i),sheetName,new String[]{sheetName});
            	}
            }
        } catch ( Exception e ) {
        	throw e;
        } finally {
        	if ( wb!=null ) {
                wb.close();
        	}
        }
	}

	//protected abstract void parsePostCode(Sheet sheet,String[] targets) throws Exception;
	protected abstract void parseSheet(Sheet sheet,String sheetName,String[] targets) throws Exception;
}
