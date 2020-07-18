package ru.otus.valeev.service;

import ru.otus.valeev.domain.AuthorMongo;

import java.util.List;

public interface AuthorService {
    List<AuthorMongo> allAuthors();

    AuthorMongo saveAuthorByName(String name);
}
