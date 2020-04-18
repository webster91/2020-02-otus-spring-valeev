package ru.otus.valeev.dao.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDao")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoTest {
    private static final int EXPECTED_AUTHORS_COUNT = 3;
    private static final long FIRST_AUTHOR_ID = 1L;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Получение всех авторов")
    void shouldGetAllAuthors() {
        List<Author> authors = authorDao.findAll();
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("Успешное получение автора по ид")
    void shouldGetAuthorById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        Author authorExcepted = authorDao.findById(FIRST_AUTHOR_ID);
        assertThat(authorExcepted).isEqualTo(author);
    }

    @Test
    @DisplayName("Не успешное получение автора по ид. Ид 0")
    void doesntShouldGetAuthorById() {
        Author author = authorDao.findById(0);
        assertThat(author).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени")
    void shouldGetAuthorByName() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        Author authorExcepted = authorDao.findByName(author.getName());
        assertThat(authorExcepted).isEqualTo(author);
    }

    @Test
    @DisplayName("Не успешное получение автора по имени. Имя null")
    void doesntShouldGetAuthorByNameNull() {
        Author authorExcepted = authorDao.findByName(null);
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Не успешное получение автора по имени. Имя не известно")
    void doesntShouldGetAuthorByNameEmpty() {
        Author authorExcepted = authorDao.findByName("Ignatik");
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное сохранение автора")
    void shouldSaveAuthorByName() {
        Author authorExcepted = authorDao.save(new Author(0, "Vasya"));
        val author = em.find(Author.class, authorExcepted.getId());
        assertThat(authorExcepted).isEqualTo(author);
    }

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.dao")
    static class BookDaoConfiguration {
    }
}