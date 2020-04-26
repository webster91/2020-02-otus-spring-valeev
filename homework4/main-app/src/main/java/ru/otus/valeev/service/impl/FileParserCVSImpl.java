package ru.otus.valeev.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.config.AppProps;
import ru.otus.valeev.constants.BundleProperties;
import ru.otus.valeev.service.FileParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class FileParserCVSImpl implements FileParser {

    private final Locale locale;
    private final AppProps props;
    private final MessageSourceAccessor messageSource;

    @Override
    public List<String[]> readAll() throws CsvException, IOException {
        String localeFileName = String.format("%s_%s.csv", props.getBaseNames().getFileName(), locale);
        InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(localeFileName);
        if (fileStream == null) {
            throw new IOException(messageSource.getMessage(BundleProperties.MESSAGE_FILE_NOT_FOUND.getPropName()));
        }
        CSVReader csvReader = new CSVReader(new InputStreamReader(fileStream));
        List<String[]> list = csvReader.readAll();
        fileStream.close();
        csvReader.close();
        return list;
    }
}