package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
