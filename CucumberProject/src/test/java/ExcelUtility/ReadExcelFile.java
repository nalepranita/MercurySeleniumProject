package ExcelUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile{
XSSFWorkbook work_book;
XSSFSheet sheet;
public ReadExcelFile(String excelfilePath) {
try {
File s = new File(excelfilePath);
FileInputStream stream = new FileInputStream(s);
FileOutputStream fout =new FileOutputStream(s);
work_book = new XSSFWorkbook(stream);
work_book.write(fout);
}
catch(Exception e) {
System.out.println(e.getMessage());
}
}
public String getData(int sheetnumber, int row, int column){
sheet = work_book.getSheetAt(sheetnumber);
String data = sheet.getRow(row).getCell(column).getStringCellValue();
return data;
} 
public int getRowCount(int sheetIndex){
int row = work_book.getSheetAt(sheetIndex).getLastRowNum();
row = row + 1;
return row;
}
public void setCellData(int sheetnumber, int colNum, int rowNum, String data){
	sheet = work_book.getSheetAt(sheetnumber);
	sheet.getRow(rowNum).getCell(colNum).setCellValue(data);

}

}
