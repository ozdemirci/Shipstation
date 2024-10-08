package com.mgdesign.utilities;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public List<String[]> readExcelData(String filePath) {
        List<String[]> data = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);  // İlk sayfa
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount; i++) {  // Başlık satırını atlıyoruz
                Row row = sheet.getRow(i);

                // İlk satırın başlık olup olmadığını kontrol ediyoruz
                if (i == 0) {
                    continue;  // Başlık satırını atla
                }

                // Hücre türünü kontrol ediyoruz
                String id = "";
                if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    id = String.valueOf((long) row.getCell(0).getNumericCellValue());  // Sayısal hücreyi String'e dönüştürüyoruz
                } else if (row.getCell(0).getCellType() == CellType.STRING) {
                    id = row.getCell(0).getStringCellValue();
                }

                // Notes hücresinin türünü kontrol ediyoruz (genellikle string olacağını varsayıyoruz)
                String notes = "";
                if (row.getCell(1).getCellType() == CellType.STRING) {
                    notes = row.getCell(1).getStringCellValue();
                } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                    notes = String.valueOf(row.getCell(1).getNumericCellValue());
                }

                // Okunan id ve notes'u listeye ekliyoruz
                data.add(new String[] {id, notes});
            }

            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
