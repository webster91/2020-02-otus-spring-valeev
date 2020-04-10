package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    Book getBookByName(String name);

    Book getBookById(long name);

    Book saveBook(Book book);

    Book updateBook(Book book);

    boolean deleteByName(String name);
}
