package ru.otus.valeev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.QuestionDao;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${correctAnswers}")
    private Integer correctAnswers;

    private QuestionDao questionDao;

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getQuestion() {
        return questionDao.getQuestions();
    }

    @Override
    public Integer getCountCorrectAnswers() {
        return correctAnswers;
    }
}
