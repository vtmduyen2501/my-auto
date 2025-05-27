package utils.helpers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelper {
    private Workbook workbook;
    private Sheet sheet;
    private Map<String, Integer> headerMap = new HashMap<>();
    private String filePath;

    // Constructor - Reads Excel file directly from the given file path, not classpath resources
    public ExcelHelper(String filePath, String sheetName) throws InvalidFormatException {
        this.filePath = filePath;

        try (InputStream inputStream = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' was not found in the file");
            }

            // Create headerMap
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    String columnName = cell.getStringCellValue().trim();
                    headerMap.put(columnName, cell.getColumnIndex());
                }
            } else {
                throw new IllegalArgumentException("Could not find Header row (row 0) in the sheet.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCellData(String columnName, int rowIndex) {
        try {
            Integer columnIndex = headerMap.get(columnName);
            if (columnIndex == null) return null;

            Row row = sheet.getRow(rowIndex);
            if (row == null) return null;

            Cell cell = row.getCell(columnIndex);
            return (cell != null) ? cell.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setCellData(String columnName, int rowIndex, String value) {
        try {
            Integer columnIndex = headerMap.get(columnName);
            if (columnIndex == null) {
            	System.out.println("Column '" + columnName + "' not found.");
                return;
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);

            Cell cell = row.getCell(columnIndex);
            if (cell == null) cell = row.createCell(columnIndex);

            cell.setCellValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    // Overwrites the file being read
//    public void save() {
//        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//            workbook.write(fileOut);
//            System.out.println("Data saved to: " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void save() {
        File originalFile = new File(filePath);
        File copyFile = new File(filePath.replace(".xlsx", "_copy.xlsx"));

        try (FileOutputStream fileOut = new FileOutputStream(originalFile)) {
            workbook.write(fileOut);
            System.out.println("✅ Data saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("⚠️ File may still be open in Excel and cannot be written: " + filePath);
            System.err.println("→ Creating a copy and saving data into it instead: " + copyFile.getName());

            try {
                // Copy file content byte-by-byte
                try (FileInputStream in = new FileInputStream(originalFile);
                     FileOutputStream out = new FileOutputStream(copyFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }

                // Now try writing data to the copy
                try (FileOutputStream copiedOut = new FileOutputStream(copyFile)) {
                    workbook.write(copiedOut);
                    System.out.println("✅ Data saved to copy file: " + copyFile.getAbsolutePath());
                }

            } catch (IOException ex) {
                System.err.println("❌ Failed to create or write to the copy file.");
                ex.printStackTrace();
            }
        }
    }


    public void printAllData() {
        for (int i = 0; i < getRowCount(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            for (int j = 0; j < getColumnCount(); j++) {
                Cell cell = row.getCell(j);
                String value = (cell != null) ? cell.toString() : "";
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public int getColumnCount() {
        Row header = sheet.getRow(0);
        return (header != null) ? header.getPhysicalNumberOfCells() : 0;
    }
}
