package ru.otus.valeev.service;

import ru.otus.valeev.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre getGenreByName(String bookName);

    List<Genre> allGenres();

    Genre saveGenreByName(String name);
}