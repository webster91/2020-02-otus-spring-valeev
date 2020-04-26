package ru.otus.valeev.dao.impl;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.QuestionDao;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.service.FileParser;
import ru.otus.valeev.util.csv.TextToQuestion;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final FileParser fileParser;

    @Override
    public List<Question> getQuestions() {
        List<String[]> strings = null;
        try {
            strings = fileParser.readAll();
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при чтении фала");
        }
        return TextToQuestion.convertToRead(strings);
    }
}