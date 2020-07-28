package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}
