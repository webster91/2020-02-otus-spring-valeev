package ru.otus.valeev.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.BookJpa;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<BookJpa, Long> {
    @EntityGraph(value = "vitaliy")
    List<BookJpa> findAll();

    @EntityGraph(value = "vitaliy")
    BookJpa findByName(String name);
}
