package ru.otus.valeev.dao.impl;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreDao")
@DataMongoTest
@Import(MongoConfig.class)
class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Получение всех жанров")
    void shouldGetAllGenres() {
        List<Genre> genres = mongoTemplate.findAll(Genre.class);
        List<Genre> genresExcepted = genreDao.findAll();
        assertThat(genres.size()).isEqualTo(genresExcepted.size());
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя null")
    void doesntShouldGetGenreByNameNull() {
        Genre genre = genreDao.findByName(null);
        assertThat(genre).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя не известно")
    void doesntShouldGetGenreByName() {
        Genre genreExcepted = genreDao.findByName("Roman4ik");
        assertThat(genreExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени.")
    void shouldGetGenreByName() {
        Genre genre = new Genre("BestEducation");
        mongoTemplate.save(genre);
        Genre genreExcepted = genreDao.findByName(genre.getName());
        assertThat(genreExcepted).isEqualTo(genre);
    }
}