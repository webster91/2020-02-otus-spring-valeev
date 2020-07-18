package ru.otus.valeev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.AuthorJpa;

public interface AuthorJpaRepository extends JpaRepository<AuthorJpa, Long> {
    AuthorJpa findByName(String name);
}