package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getGenreByName(String name);

    Genre getGenreById(long id);

    List<Genre> getAll();

    Genre saveGenreByName(String name);
}
