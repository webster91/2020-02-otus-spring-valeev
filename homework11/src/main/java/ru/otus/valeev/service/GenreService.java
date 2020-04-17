package ru.otus.valeev.service;

import ru.otus.valeev.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> allGenres();

    Genre saveGenreByName(String name);
}