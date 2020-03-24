package ru.otus.valeev.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuizException extends RuntimeException {

    public QuizException(String ex) {
        super(ex);
    }
}
