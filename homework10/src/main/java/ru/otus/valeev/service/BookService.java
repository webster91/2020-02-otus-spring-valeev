package ru.otus.valeev.service;

import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String name);

    Book findById(String id);

    Book save(String bookName, String authorName, String genreName);

    Book save(Book book);

    Book update(String bookId, String bookName, String authorName, String genreName);

    boolean deleteById(String bookId);

    List<Comment> findCommentsByBookId(String bookId);
}
