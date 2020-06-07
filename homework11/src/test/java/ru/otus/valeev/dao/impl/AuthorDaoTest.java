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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

@DisplayName("AuthorDao")
@DataMongoTest
@Import(MongoConfig.class)
@ExtendWith(SpringExtension.class)
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Получение всех авторов")
    void shouldGetAllAuthors() {
        Flux<Author> authorsFlux = authorDao.findAll();

        StepVerifier
                .create(authorsFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя null")
    void doesntShouldGetAuthorByNameNull() {
        Flux<Author> author = authorDao.findByName(null);
        StepVerifier
                .create(author)
                .verifyComplete();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя не известно")
    void doesntShouldGetAuthorByNameEmpty() {
        Flux<Author> author = authorDao.findByName("Ignatik");
        StepVerifier
                .create(author)
                .verifyComplete();
    }

    @Test
    @DisplayName("Успешное получение автора по имени.")
    void shouldGetAuthorByNameEmpty() {
        String name = "Ignat";
        Flux<Author> author = authorDao.findByName(name);
        StepVerifier
                .create(author)
                .expectNextMatches(author1 -> name.equals(author1.getName()))
                .verifyComplete();
    }
}