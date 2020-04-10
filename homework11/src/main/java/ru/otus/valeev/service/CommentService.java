package ru.otus.valeev.service;

import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getById(long id);

    List<Comment> getByBookName(String bookName);

    boolean deleteCommentById(long id);

    Comment addComment(String bookName, String comment);
}
