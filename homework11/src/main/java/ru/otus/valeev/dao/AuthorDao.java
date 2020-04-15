package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
