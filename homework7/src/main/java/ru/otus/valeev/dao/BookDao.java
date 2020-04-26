package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {
    @EntityGraph(value = "vitaliy")
    List<Book> findAll();

    @EntityGraph(value = "vitaliy")
    Book findByName(String name);
}
