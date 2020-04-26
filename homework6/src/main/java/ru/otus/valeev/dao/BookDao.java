package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();

    Book findByName(String name);

    Book findById(long name);

    Book save(Book book);

    Book deleteById(Long bookId);

    Book delete(Book book);
}
