package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import ru.otus.valeev.constants.BundleProperties;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.exception.QuizException;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.QuestionService;

import java.util.List;

@AllArgsConstructor
@Controller
public class QuizController {

    private final QuestionService questionService;
    private final ConsoleService consoleService;
    private final MessageSourceAccessor messageSource;

    public void playQuiz() {
        consoleService.sendMessage(messageSource.getMessage(BundleProperties.MESSAGE_ENTER_NAME.getPropName()));
        String name = consoleService.getMessage();
        if (StringUtils.isBlank(name)) {
            throw new QuizException(messageSource.getMessage(BundleProperties.MESSAGE_WRONG_NAME.getPropName()));
        }
        List<Question> questions = questionService.getQuestion();
        int wrongAnswerCount = 0;
        for (Question question : questions) {
            consoleService.sendMessage(question.getQuestion());
            String answer = consoleService.getMessage();
            String messageAnswer = messageSource.getMessage(BundleProperties.MESSAGE_SUCCESS.getPropName());
            if (!question.getAnswer().equals(answer)) {
                messageAnswer = messageSource.getMessage(BundleProperties.MESSAGE_WRONG.getPropName(),
                        new String[]{question.getAnswer()});
                ++wrongAnswerCount;
            }
            consoleService.sendMessage(messageAnswer);
        }
        int correctAnswers = questions.size() - wrongAnswerCount;
        String message;
        if (correctAnswers >= questionService.getCountCorrectAnswers()) {
            message = messageSource.getMessage(BundleProperties.MESSAGE_SUCCESS_RESULT.getPropName());
        } else {
            message = messageSource.getMessage(BundleProperties.MESSAGE_FAIL_RESULT.getPropName());
        }
        consoleService.sendMessage(messageSource.getMessage(
                BundleProperties.MESSAGE_END_GAME.getPropName(),
                new String[]{name, message, String.valueOf(correctAnswers), String.valueOf(wrongAnswerCount)}));
    }
}
