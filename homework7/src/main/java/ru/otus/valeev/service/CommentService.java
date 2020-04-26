package ru.otus.valeev.service;

import ru.otus.valeev.domain.Comment;

public interface CommentService {
    boolean deleteById(long id);

    Comment save(Long bookId, String comment);
}