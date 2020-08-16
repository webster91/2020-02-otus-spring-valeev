package ru.otus.valeev.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.GenreService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    @HystrixCommand(commandKey = "findAll", fallbackMethod = "fallbackGetAuthors")
    public List<Genre> allGenres() {
        return genreDao.findAll();
    }

    @Override
    @Transactional
    public Genre saveGenreByName(String genreName) {
        Genre genre = genreDao.findByName(genreName);
        if (genre == null) {
            genre = genreDao.save(new Genre(genreName));
        }
        return genre;
    }

    private List<Genre> fallbackGetAuthors() {
        Genre genre = new Genre();
        genre.setId("0");
        genre.setName("Дорама");
        return Collections.singletonList(genre);
    }
}
