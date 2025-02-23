package com.example.teste.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class FileProcessorService {

    public FileProcessorService(){

    }

    public static List<String[]> readFile(File file) throws IOException {
        List<String[]> data = new ArrayList<>();

        if (file.getName().endsWith(".csv")) {
            data = readCsv(file, ";");
        } else if (file.getName().endsWith(".xlsx")) {
            data = readXlsx(file);
        }

        return data;
    }

    private static List<String[]> readCsv(File file, String delimiter) throws IOException {
        List<String[]> lines = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(file.toPath());
        for (String line : fileLines) {
            lines.add(line.split(delimiter));
        }
        return lines;
    }

    private static List<String[]> readXlsx(File file) throws IOException {
        List<String[]> lines = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                lines.add(rowData.toArray(new String[0]));
            }
        }
        return lines;
    }


}
