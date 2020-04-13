package ru.otus.valeev.service;

import ru.otus.valeev.domain.Comment;

public interface CommentService {
    Comment findById(long id);

    Comment deleteById(long id);

    Comment save(String bookName, String comment);
}
