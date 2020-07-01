package ru.otus.valeev.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.BookMongo;

public interface BookMongoRepository extends MongoRepository<BookMongo, Long> {
    BookMongo findByName(String name);
}
