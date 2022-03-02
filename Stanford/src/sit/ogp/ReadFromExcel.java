package sit.ogp;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadFromExcel extends Run {

	public static Sheet sheet;
	public static Workbook workbook;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	public static String sActionKeyword=null;
	

	public List<Map<String,String>>getTestDataInMap() throws Exception {
		
		Map<String, String>testData=null;
		
		testData=new HashMap<String,String>();
		try {
			FileInputStream fileInputStream=new FileInputStream("TestData\\TestData.xlsx");
			workbook=new XSSFWorkbook(fileInputStream);

			sheet = workbook.getSheetAt(0);
			int lastRowNumber=sheet.getLastRowNum();

			int lastColNumber=sheet.getRow(0).getLastCellNum();

			List<String> list =new ArrayList<String>();
			for(int i=0;i<lastColNumber;i++) {
				Row row=sheet.getRow(0);
				Cell cell=row.getCell(i);
				String rowHeader=cell.getStringCellValue().trim();
				list.add(rowHeader);

			}
			
			for(int j=1;j<=lastRowNumber;j++) {
				Row row=sheet.getRow(j);
				testData=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				for(int k=0;k<lastColNumber;k++) {
					Cell cell=row.getCell(k);
					String colValue=cell.getStringCellValue().trim();
					testData.put((String) list.get(k), colValue);

				}

				testDataAllRows.add(testData);
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testDataAllRows;
	}

	public static void main(String[] args) throws Exception
	{

		ReadFromExcel readExcel= new ReadFromExcel();

		System.out.println(readExcel.getTestDataInMap());


	}


}
