package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();

    Genre findByName(String name);

    Genre findById(long id);

    Genre save(Genre name);
}
