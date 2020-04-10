package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAllAuthors();

    Author getAuthorById(long id);

    Author getAuthorByName(String name);

    Author saveAuthorByName(String name);
}
