package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.valeev.domain.Genre;

import java.util.List;

public interface GenreDao extends JpaRepository<Genre, Long> {
    Genre findByName(String name);

    Genre findById(long id);
}
