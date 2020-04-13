package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Genre getGenreByName(String bookName) {
        return genreDao.findByName(bookName);
    }

    @Override
    public List<Genre> allGenres() {
        return genreDao.findAll();
    }

    @Override
    public Genre saveGenreByName(String genreName) {
        Genre genre = genreDao.findByName(genreName);
        if (genre == null) {
            genre = genreDao.save(Genre.builder()
                    .name(genreName)
                    .build());
        }
        return genre;
    }
}
