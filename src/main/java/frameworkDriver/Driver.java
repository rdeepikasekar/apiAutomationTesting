package frameworkDriver;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Driver {
	static DataFormatter df = new DataFormatter();
	
	@Test
	public static void driverMtd(String TestCaseNo, String Flag, String TestCaseName,String Index) {
		Driver.readExecutionSheet();
		System.out.println(Flag);
		System.out.println(TestCaseName);
		System.out.println(Index);
		
		if((Flag.equals("Yes")) == true) {
			Driver.executeScript(TestCaseName);
			System.out.println("===============================================");
		}
	}
	
	
	@Test
	public static void executeScript(String TestCaseName){
		try {
			String ScriptFilePath = "./src/main/resources/ScriptSheet/"+TestCaseName.trim()+".xlsx";
			FileInputStream fs = new FileInputStream(ScriptFilePath);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet ws = wb.getSheetAt(0);
			int rowCount = ws.getLastRowNum();
			int colCount = ws.getRow(0).getLastCellNum();
			
			for(int rowCounter = 1;rowCounter<=rowCount;rowCounter++) {
				Row row = ws.getRow(rowCounter);
				String StepNo = df.formatCellValue(row.getCell(0));
				String TestStepDescription = df.formatCellValue(row.getCell(1));
				String MethodName = df.formatCellValue(row.getCell(2));
				String InputParameter = df.formatCellValue(row.getCell(3));
				String OutputParameter = df.formatCellValue(row.getCell(4));
				System.out.println("MethodName - "+MethodName);
				
				//fetch test data
				Map<String,String> map = Driver.fetchTestData(TestCaseName);
				
				map.put("para", "1");
				Method method = AutomationScript.Script.class.getDeclaredMethod(MethodName,Map.class);
				method.setAccessible(true);
				method.invoke(AutomationScript.Script.class.getDeclaredConstructor().newInstance(), map);
				
			   
			}
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public static Map<String,String> fetchTestData(String TestCaseName){
		FileInputStream fs;
		try {
			fs = new FileInputStream("./src/main/resources/TestData/TestData.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet ws = wb.getSheet("InputData");
			int rowCount = ws.getLastRowNum();
			
			Map<String,String> map = new HashMap<String,String>();
			for(int rowCounter = 0;rowCounter <= rowCount;rowCounter++) {
				Row row = ws.getRow(rowCounter);
				Cell TCNameCell = row.getCell(0);
				String TCName = df.formatCellValue(TCNameCell);
				if(TCName.equals(TestCaseName) == true) {
					Row VarRow = ws.getRow(rowCounter-1);
					
					int intColCount = VarRow.getLastCellNum();
					for(int colCounter = 0;colCounter<=intColCount;colCounter++) {
						Cell VarNameCell = VarRow.getCell(colCounter);
						Cell VarValCell = row.getCell(colCounter);
						
						String VarName = df.formatCellValue(VarNameCell);
						String VarVal = df.formatCellValue(VarValCell);
						
						map.put(VarName, VarVal);
						
					}
				}
			}
			return map;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@DataProvider
	public static Object[][] readExecutionSheet() {
		String FilePath = "./src/main/resources/ConfigSheet/ExecutionSheet.xlsx";
		try {
			FileInputStream fs = new FileInputStream(FilePath);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet ws = wb.getSheetAt(0);
			int rowCount = ws.getLastRowNum();
			int colCount = ws.getRow(0).getLastCellNum();
			
			Object Data[][] = new Object[rowCount][colCount];
			for(int rowCounter = 0;rowCounter<rowCount;rowCounter++) {
				Row row = ws.getRow(rowCounter+1);
				for(int colCounter = 0;colCounter<colCount;colCounter++) {
					Cell cell = row.getCell(colCounter);
					String value = df.formatCellValue(cell);
					Data[rowCounter][colCounter] = value;
				}
			}
			return Data;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
