package ru.otus.valeev.service;

import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentService {
    boolean deleteById(String id);

    List<Comment> deleteCommentsByBookId(String id);

    Comment save(String bookId, String comment);
}