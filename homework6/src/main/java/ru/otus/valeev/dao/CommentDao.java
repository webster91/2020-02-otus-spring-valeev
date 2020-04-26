package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Comment;

public interface CommentDao {
    Comment findById(long id);

    Comment save(Comment comment);

    Comment deleteById(long bookId);
}
