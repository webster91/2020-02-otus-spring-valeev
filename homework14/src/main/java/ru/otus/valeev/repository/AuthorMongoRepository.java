package ru.otus.valeev.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.AuthorMongo;

public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, Long> {
    AuthorMongo findByName(String name);
}