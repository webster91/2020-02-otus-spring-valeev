package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.Book;

public interface BookDao extends MongoRepository<Book, String> {
    Book findByName(String name);
}
