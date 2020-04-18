package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAll();

    Author findById(long id);

    Author findByName(String name);

    Author save(Author author);
}
