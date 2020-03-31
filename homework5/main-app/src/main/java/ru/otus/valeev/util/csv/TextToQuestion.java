package ru.otus.valeev.util.csv;

import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;
import ru.otus.valeev.domain.Question;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TextToQuestion {

    public Question convertToRead(String[] value) {
        if (value == null || value.length != 2) {
            return null;
        }
        String question = value[0];
        String answer = value[1];
        return new Question(question, answer);
    }

    public List<Question> convertToRead(List<String[]> values) {
        List<Question> questions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(values)) {
            values.forEach(x -> questions.add(convertToRead(x)));
        }
        return questions;
    }
}
