package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.Author;

public interface AuthorDao extends MongoRepository<Author, String> {
    Author findByName(String name);
}