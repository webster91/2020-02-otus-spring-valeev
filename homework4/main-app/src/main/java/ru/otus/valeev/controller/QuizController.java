package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
import ru.otus.valeev.constants.BundleProperties;
import ru.otus.valeev.domain.Player;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.exception.QuizException;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.PlayerService;
import ru.otus.valeev.service.QuestionService;

import java.util.List;

@AllArgsConstructor
@Controller
@ShellComponent
public class QuizController {

    private final QuestionService questionService;
    private final ConsoleService consoleService;
    private final MessageSourceAccessor messageSource;
    private final PlayerService playerService;

    @ShellMethod(value = "Play the quiz", key = {"p", "play"})
    @ShellMethodAvailability(value = "validUserName")
    public void playQuiz() {
        if (StringUtils.isBlank(playerService.getPlayerName())) {
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
                new String[]{playerService.getPlayerName(), message, String.valueOf(correctAnswers), String.valueOf(wrongAnswerCount)}));
    }

    @ShellMethod(value = "register player", key = {"n", "name", "r", "register"})
    public void register(@ShellOption String playerName) {
        Player player = playerService.registerPlayer(playerName);
        if (player == null) {
            throw new QuizException(messageSource.getMessage(BundleProperties.MESSAGE_WRONG_NAME.getPropName()));
        }
    }

    private Availability validUserName() {
        return StringUtils.isBlank(playerService.getPlayerName()) ?
                Availability.unavailable(messageSource.getMessage(
                        BundleProperties.MESSAGE_FAIL_ENTER_USERNAME.getPropName())) :
                Availability.available();
    }
}
