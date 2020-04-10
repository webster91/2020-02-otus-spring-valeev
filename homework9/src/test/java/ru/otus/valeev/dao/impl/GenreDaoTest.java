package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreDaoJdbc")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoTest {
    private static final int EXPECTED_GENRE_COUNT = 3;
    private static final long FIRST_GENRE_ID = 1L;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Успешное получение жанра по ИД")
    void shouldSaveGenreByName() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        Genre genreExcepted = genreDao.getGenreByName(genre.getName());
        assertThat(genreExcepted).isEqualTo(genre);
    }

    @Test
    @DisplayName("Не успешное получение жанров по имени. Не существующиее имя")
    void doesntShouldSaveGenreByName() {
        Genre genreExcepted = genreDao.getGenreByName("Horizon zero");
        assertThat(genreExcepted).isNull();
    }

    @Test
    @DisplayName("Успешное получение жанров по ИД")
    void shouldGetGenreById() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        Genre genreExcepted = genreDao.getGenreById(genre.getId());
        assertThat(genre).isEqualTo(genreExcepted);
    }

    @Test
    @DisplayName("Не успешное получение жанров по ИД. Не существующий ИД")
    void doesntShouldGetGenreById() {
        Genre genreExcepted = genreDao.getGenreById(54);
        assertThat(genreExcepted).isNull();
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