package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.valeev.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findByName(String name);
}