package ru.otus.valeev.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.constants.BundleProperties;
import ru.otus.valeev.service.FileParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class FileParserCVSImpl implements FileParser {

    @Value("${baseNames.fileName}")
    private String baseNames;

    private Locale locale;

    private MessageSourceAccessor messageSource;

    @Autowired
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Autowired
    public void setMessageSource(MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<String[]> readAll() throws CsvException, IOException {
        String localeFileName = String.format("%s_%s.csv", baseNames, locale);
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