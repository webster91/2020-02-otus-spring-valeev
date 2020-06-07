package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.valeev.domain.Genre;

public interface GenreDao extends ReactiveMongoRepository<Genre, String> {
    Genre findByName(String name);
}
