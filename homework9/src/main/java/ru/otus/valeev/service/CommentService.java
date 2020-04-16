package ru.otus.valeev.service;

import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment findById(long id);

    Comment deleteById(long id);

    Comment save(String bookName, String comment);

    List<Comment> findCommentsByBookName(String name);
}
