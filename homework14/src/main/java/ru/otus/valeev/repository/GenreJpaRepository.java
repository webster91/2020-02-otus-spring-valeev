package ru.otus.valeev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.GenreJpa;

public interface GenreJpaRepository extends JpaRepository<GenreJpa, Long> {
    GenreJpa findByName(String name);
}
