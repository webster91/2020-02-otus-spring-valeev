package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDao")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final int EXPECTED_AUTHORS_COUNT = 3;
    private static final Author DEFAULT_AUTHOR = new Author(2, "Игнат");

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Получение всех авторов")
    void shouldGetAllAuthors() {
        List<Author> authors = authorDao.getAllAuthors();
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("Успешное получение автора по ид")
    void shouldGetAuthorById() {
        Author author = authorDao.saveAuthorByName(DEFAULT_AUTHOR.getName());
        Author authorExcepted = authorDao.getAuthorById(author.getId());
        assertThat(authorExcepted).isEqualTo(author);
    }

    @Test
    @DisplayName("Не успешное получение автора по ид. Ид 0")
    void doesntShouldGetAuthorById() {
        Author author = authorDao.getAuthorById(0);
        assertThat(author).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени")
    void shouldGetAuthorByName() {
        Author author = authorDao.saveAuthorByName(DEFAULT_AUTHOR.getName());
        Author authorExcepted = authorDao.getAuthorByName(DEFAULT_AUTHOR.getName());
        assertThat(authorExcepted).isEqualTo(author);
    }

    @Test
    @DisplayName("Не успешное получение автора по имени. Имя null")
    void doesntShouldGetAuthorByNameNull() {
        Author authorExcepted = authorDao.getAuthorByName(null);
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Не успешное получение автора по имени. Имя не известно")
    void doesntShouldGetAuthorByNameEmpty() {
        Author authorExcepted = authorDao.getAuthorByName("Ignatik");
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное сохранение автора")
    void shouldSaveAuthorByName() {
        Author author = authorDao.saveAuthorByName(DEFAULT_AUTHOR.getName());
        Author authorExcepted = authorDao.getAuthorByName(DEFAULT_AUTHOR.getName());
        assertThat(authorExcepted).isEqualTo(author);
    }

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.dao")
    static class BookDaoConfiguration {
    }
}