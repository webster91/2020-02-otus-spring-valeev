package ru.otus.valeev.dao.impl;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDao")
@DataMongoTest
@Import(MongoConfig.class)
@ExtendWith(SpringExtension.class)
class AuthorDaoMongoTest {

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Получение всех авторов")
    void shouldGetAllAuthors() {
        List<Author> authors = mongoTemplate.findAll(Author.class);
        List<Author> authorsExcepted = authorDao.findAll();
        assertThat(authors.size()).isEqualTo(authorsExcepted.size());
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя null")
    void doesntShouldGetAuthorByNameNull() {
        Author author = authorDao.findByName(null);
        assertThat(author).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя не известно")
    void doesntShouldGetAuthorByNameEmpty() {
        Author authorExcepted = authorDao.findByName("Ignatik");
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени.")
    void shouldGetAuthorByNameEmpty() {
        Author author = new Author("Vasiliy");
        mongoTemplate.save(author);
        Author authorExcepted = authorDao.findByName(author.getName());
        assertThat(authorExcepted).isEqualTo(author);
    }
}