


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UploadExcel {

    private static final String FILE_NAME = "D:\\SHETTY\\Hub_Excel.xlsx";

    public static void main(String[] args) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int i=0;
            List<HubPodConfiguration> hubPodConfigurationList=new ArrayList<HubPodConfiguration>();
            String columnNames[]=null;
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                
                
                
                if(i==0)
                {
                	columnNames= getColumnNames(currentRow);
                }
                Iterator<Cell> cellIterator = currentRow.iterator();
                
                i++;
                boolean flag=false;
                HubPodConfiguration hubPodConfiguration=new HubPodConfiguration();
                int j=0;
                while (cellIterator.hasNext() && i>1) {
                	
                	
                	flag=true;
                    Cell currentCell = cellIterator.next();
                    String columnName = columnNames[j++];
                    java.lang.reflect.Field f1;
					try {
						f1 = hubPodConfiguration.getClass().getDeclaredField(columnName.trim());
						f1.setAccessible(true);
	                    f1.set(hubPodConfiguration, getTypeValue(f1.getType(),currentCell));
	                    
	                    getTypeValue(f1.getType(), currentCell);
	                    
	                    
					} catch (NoSuchFieldException e) {
						 
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    

                }
                
                if(i>2)
                {
                	hubPodConfigurationList.add(hubPodConfiguration);
                }
                 

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private static String[] getColumnNames(Row column) {
        String columns[] = new String[column.getPhysicalNumberOfCells()];
        Iterator<Cell> cellIterator = column.cellIterator();
        int i = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columns[i++] = cell.getStringCellValue();
        }        
        
        return columns;
    }
    
    /**
     * Method to get typed value
     * @param type
     * @param value 
     * @return
     */
    private static Object getTypeValue(Class<?> type, Cell cell) {
        Object typedValue = null;
        if(type == int.class){
            typedValue = (int) cell.getNumericCellValue();
        } else if(type == double.class){
            typedValue = cell.getNumericCellValue();
        } else if(type == boolean.class){
            typedValue = cell.getBooleanCellValue();
        } else if(type == String.class){
            typedValue = cell.getStringCellValue();
        }
        return typedValue;
    }    
    
}



