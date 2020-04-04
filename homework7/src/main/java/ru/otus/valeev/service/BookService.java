package ru.otus.valeev.service;

import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookByName(String name);

    Book saveBook(String bookName, String authorName, String genreName);

    Book updateBook(String bookName, String authorName, String genreName);

    boolean deleteBook(String name);
}
