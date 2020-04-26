package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {
    Genre findByName(String name);
}
