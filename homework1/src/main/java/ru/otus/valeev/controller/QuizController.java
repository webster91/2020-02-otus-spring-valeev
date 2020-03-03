package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.exception.QuizException;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.QuestionService;

import java.util.List;

@AllArgsConstructor
public class QuizController {
    private static final String SUCCESS_MESSAGE = "Правилльный ответ";
    private static final String WRONG_MESSAGE = "Ошибочка, правильный ответ %s";
    private static final String END_GAME_MESSAGE = "Игра закончена %s";
    private static final String ENTER_NAME_MESSAGE = "Привет введи своё имя";
    private static final String WRONG_NAME_MESSAGE = "Имя не задано или введено не корректно";

    QuestionService questionService;
    ConsoleService consoleService;

    public void playQuiz() {
        consoleService.sendMessage(ENTER_NAME_MESSAGE);
        String name = consoleService.getMessage();
        if (StringUtils.isBlank(name)) {
            throw new QuizException(WRONG_NAME_MESSAGE);
        }
        List<Question> questions = questionService.getQuestion();
        questions.forEach(question -> {
            consoleService.sendMessage(question.getQuestion());
            String answer = consoleService.getMessage();
            String messageAnswer = SUCCESS_MESSAGE;
            if (!question.getAnswer().equals(answer)) {
                messageAnswer = String.format(WRONG_MESSAGE, question.getAnswer());
            }
            consoleService.sendMessage(messageAnswer);
        });
        consoleService.sendMessage(String.format(END_GAME_MESSAGE, name));
    }
}
