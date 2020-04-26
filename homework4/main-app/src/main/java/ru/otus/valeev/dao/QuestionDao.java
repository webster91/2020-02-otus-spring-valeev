package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions();
}
