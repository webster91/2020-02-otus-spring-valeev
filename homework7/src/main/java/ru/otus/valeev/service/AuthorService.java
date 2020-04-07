package ru.otus.valeev.service;

import ru.otus.valeev.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> allAuthors();

    Author getAuthorByName(String name);

    Author getAuthorById(Long id);

    Author saveAuthorByName(String name);
}
