package ru.otus.valeev.service;

import ru.otus.valeev.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> allAuthors();

    Author saveAuthorByName(String name);
}
