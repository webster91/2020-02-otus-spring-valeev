package ru.otus.valeev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.CommentJpa;

public interface CommentJpaRepository extends JpaRepository<CommentJpa, Long> {
}
