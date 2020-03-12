package ru.otus.valeev.dao.impl;

import com.opencsv.exceptions.CsvException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.QuestionDao;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.service.FileParser;
import ru.otus.valeev.util.csv.TextToQuestion;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionDaoImpl implements QuestionDao {

    FileParser fileParser;
    TextToQuestion textToQuestion;

    @Override
    public List<Question> getQuestions() {
        List<String[]> strings = null;
        try {
            strings = fileParser.readAll();
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при чтении фала");
        }
        return textToQuestion.convertToRead(strings);
    }
}
