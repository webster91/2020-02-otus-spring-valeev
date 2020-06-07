package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.valeev.domain.Book;

public interface BookDao extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByName(String name);
}
