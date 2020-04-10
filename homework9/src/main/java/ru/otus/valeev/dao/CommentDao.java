package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentDao {
    Comment getCommentById(long id);

    Comment addComment(Comment comment);

    List<Comment> getCommentsByBookId(long bookId);

    boolean deleteCommentById(long bookId);
}
