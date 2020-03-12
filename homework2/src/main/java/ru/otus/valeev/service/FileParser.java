package ru.otus.valeev.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface FileParser {
    List<String[]> readAll() throws CsvException, IOException;
}
