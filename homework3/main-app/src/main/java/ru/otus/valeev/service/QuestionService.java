package ru.otus.valeev.service;

import ru.otus.valeev.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestion();
    Integer getCountCorrectAnswers();
}
