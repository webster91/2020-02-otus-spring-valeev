package ru.otus.valeev.service;

import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String name);

    Book findById(Long id);

    Book save(String bookName, String authorName, String genreName);

    Book update(Long bookId, String authorName, String genreName);

    boolean deleteById(Long bookId);

    List<Comment> findCommentsByBookId(Long bookId);
}
