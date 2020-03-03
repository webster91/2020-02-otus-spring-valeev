package ru.otus.valeev.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import ru.otus.valeev.service.FileParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@AllArgsConstructor
public class FileParserCVSImpl implements FileParser {
    private String fileName;

    @Override
    public List<String[]> readAll() throws CsvException, IOException {
        InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (fileStream == null) {
            throw new IOException("Файл с вопросами не найден");
        }
        CSVReader csvReader = new CSVReader(new InputStreamReader(fileStream));
        List<String[]> list = csvReader.readAll();
        fileStream.close();
        csvReader.close();
        return list;
    }
}
