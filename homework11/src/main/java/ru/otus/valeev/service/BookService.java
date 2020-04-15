package ru.otus.valeev.service;

import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String name);

    Book save(String bookName, String authorName, String genreName);

    Book update(String bookName, String authorName, String genreName);

    Long deleteByName(String name);
}
