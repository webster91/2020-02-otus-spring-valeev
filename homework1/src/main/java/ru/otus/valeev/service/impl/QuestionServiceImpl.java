package ru.otus.valeev.service.impl;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.service.FileParser;
import ru.otus.valeev.service.QuestionService;
import ru.otus.valeev.util.csv.TextToQuestion;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    FileParser fileParser;
    TextToQuestion textToQuestion;

    @Override
    public List<Question> getQuestion() {
        List<String[]> strings = null;
        try {
            strings = fileParser.readAll();
        } catch (IOException | URISyntaxException | CsvException e) {
            System.out.println("Ошибка при чтении фала");
        }
        return textToQuestion.convertToRead(strings);
    }
}
