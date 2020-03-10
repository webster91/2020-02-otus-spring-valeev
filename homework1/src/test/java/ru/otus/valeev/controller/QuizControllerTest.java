package ru.otus.valeev.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.valeev.domain.Question;
import ru.otus.valeev.exception.QuizException;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.QuestionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс QuizController")
class QuizControllerTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private ConsoleService consoleService;

    private QuizController quizController;

    @BeforeEach
    void setUp() {
        quizController = new QuizController(questionService, consoleService);
    }

    @DisplayName("Проверка некорректного задания имени пользователя")
    @Test
    void errorInputName() {
        String name = "";
        doNothing().when(consoleService).sendMessage(any());

        given(consoleService.getMessage())
                .willReturn(name);

        assertThrows(QuizException.class, () -> quizController.playQuiz());
    }

    @DisplayName("Проверка успешного выполнения сценария")
    @Test
    void optimisticQuizWithoutThrows() {
        String name = "Ignat";
        List<Question> questions = Collections.singletonList(new Question("1 + 1", "2"));
        doNothing().when(consoleService).sendMessage(any());

        given(consoleService.getMessage())
                .willReturn(name);

        given(questionService.getQuestion())
                .willReturn(questions);

        assertDoesNotThrow(() -> quizController.playQuiz());
    }
}