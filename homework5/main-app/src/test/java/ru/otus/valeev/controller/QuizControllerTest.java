package ru.otus.valeev.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.exception.QuizException;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.PlayerService;
import ru.otus.valeev.service.QuestionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@DisplayName("Класс QuizController")
@SpringBootTest
class QuizControllerTest {

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.controller")
    static class QuizConfiguration {}

    @Autowired
    private QuizController quizController;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private ConsoleService consoleService;
    @MockBean
    private MessageSourceAccessor messageSource;
    @MockBean
    private PlayerService playerService;

    @DisplayName("Проверка регистрации пустого имени пользователя")
    @Test
    void registerEmptyNameUser() {
        String name = "";
        String message = "default";
        given(playerService.getPlayerName())
                .willReturn(name);
        given(messageSource.getMessage(anyString()))
                .willReturn(message);

        assertThrows(QuizException.class, () -> quizController.register(name));
    }

    @DisplayName("Проверка регистрации без указания имени пользователя")
    @Test
    void registerNullNameUser() {
        String message = "default";
        given(playerService.getPlayerName())
                .willReturn(null);
        given(messageSource.getMessage(anyString()))
                .willReturn(message);

        assertThrows(QuizException.class, () -> quizController.register(null));
    }

    @DisplayName("Проверка успешного выполнения сценария")
    @Test
    void optimisticQuizWithoutThrows() {
        String name = "Ignat";
        List<Question> questions = Collections.singletonList(new Question("1 + 1", "2"));
        doNothing().when(consoleService).sendMessage(any());
        given(consoleService.getMessage())
                .willReturn("Answer user");
        given(questionService.getQuestion())
                .willReturn(questions);
        given(playerService.getPlayerName())
                .willReturn(name);

        assertDoesNotThrow(() -> quizController.playQuiz());
    }
}