package ru.otus.valeev.service;

import ru.otus.valeev.domain.BookJpa;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.domain.CommentMongo;

import java.util.List;

public interface BookService {
    List<BookMongo> findAll();

    List<BookJpa> findAllJpa();

    BookMongo findByName(String name);

    BookMongo findById(Long id);

    BookMongo save(String bookName, String authorName, String genreName);

    BookMongo save(BookMongo bookMongo);

    BookMongo update(Long bookId, String authorName, String genreName);

    boolean deleteById(Long bookId);

    List<CommentMongo> findCommentsByBookId(Long bookId);
}
