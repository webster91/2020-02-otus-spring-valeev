package ru.otus.valeev.service;

import ru.otus.valeev.domain.CommentMongo;

import java.util.List;

public interface CommentService {
    boolean deleteById(Long id);

    List<CommentMongo> deleteCommentsByBookId(Long id);

    CommentMongo save(Long bookId, String comment);
}