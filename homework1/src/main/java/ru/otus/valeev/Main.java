package ru.otus.valeev;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.valeev.controller.QuizController;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizController quizController = context.getBean(QuizController.class);
        quizController.playQuiz();
    }
}
