package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.valeev.domain.Author;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Long> {

    Author getAuthorById(long id);

    Author getAuthorByName(String name);
}
