package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreDaoJdbc")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final int EXPECTED_GENRE_COUNT = 3;
    private static final Genre DEFAULT_GENRE = new Genre(4, "Роман");

    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("Успешное получение жанра по ИД")
    void shouldSaveGenreByName() {
        Genre genre = genreDao.saveGenreByName(DEFAULT_GENRE.getName());
        Genre genreExcepted = genreDao.getGenreByName(genre.getName());
        assertThat(genreExcepted).isEqualTo(genre);
    }

    @Test
    @DisplayName("Не успешное получение жанров по имени. Не существующиее имя")
    void doesntShouldSaveGenreByName() {
        Genre genre = genreDao.saveGenreByName(DEFAULT_GENRE.getName());
        Genre genreExcepted = genreDao.getGenreByName(genre.getName());
        assertThat(genreExcepted).isEqualTo(genre);
    }

    @Test
    @DisplayName("Успешное получение жанров по ИД")
    void shouldGetGenreById() {
        Genre genre = genreDao.saveGenreByName(DEFAULT_GENRE.getName());
        Genre genreExcepted = genreDao.getGenreById(genre.getId());
        assertThat(genre).isEqualTo(genreExcepted);
    }

    @Test
    @DisplayName("Не успешное получение жанров по ИД. Не существующий ИД")
    void doesntShouldGetGenreById() {
        Genre genreExcepted = genreDao.getGenreById(54);
        assertThat(genreExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение всех жанров")
    void shouldGetAll() {
        List<Genre> genres = genreDao.getAll();
        assertThat(genres.size()).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.dao")
    static class BookDaoConfiguration {
    }
}