package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.domain.GenreMongo;
import ru.otus.valeev.repository.GenreMongoRepository;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreMongoRepository genreMongoRepository;

    @Override
    public List<GenreMongo> allGenres() {
        return genreMongoRepository.findAll();
    }

    @Override
    @Transactional
    public GenreMongo saveGenreByName(String genreName) {
        GenreMongo genreMongo = genreMongoRepository.findByName(genreName);
        if (genreMongo == null) {
            genreMongo = genreMongoRepository.save(new GenreMongo(genreName));
        }
        return genreMongo;
    }
}
