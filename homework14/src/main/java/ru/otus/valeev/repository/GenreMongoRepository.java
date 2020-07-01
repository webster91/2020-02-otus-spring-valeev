package ru.otus.valeev.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.GenreMongo;

public interface GenreMongoRepository extends MongoRepository<GenreMongo, Long> {
    GenreMongo findByName(String name);
}
