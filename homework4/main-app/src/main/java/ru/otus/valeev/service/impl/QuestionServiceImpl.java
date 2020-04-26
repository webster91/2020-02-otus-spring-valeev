package ru.otus.valeev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.config.AppProps;
import ru.otus.valeev.dao.QuestionDao;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.service.QuestionService;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final AppProps props;
    private final QuestionDao questionDao;

    @Override
    public List<Question> getQuestion() {
        return questionDao.getQuestions();
    }

    @Override
    public Integer getCountCorrectAnswers() {
        return props.getCorrectAnswers();
    }
}
