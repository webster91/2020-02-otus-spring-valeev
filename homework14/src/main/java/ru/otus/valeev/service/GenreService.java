package ru.otus.valeev.service;

import ru.otus.valeev.domain.GenreMongo;

import java.util.List;

public interface GenreService {

    List<GenreMongo> allGenres();

    GenreMongo saveGenreByName(String name);
}