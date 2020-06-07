package ru.otus.valeev.dao.impl;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;

@DisplayName("BookDao")
@DataMongoTest
@Import(MongoConfig.class)
class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Успешное получение всех книг")
    void shouldGetAllBooks() {
        Flux<Book> books = bookDao.findAll();
        StepVerifier
                .create(books)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Успешное получение книги по имени")
    void shouldGetBookByName() {
        String name = "Book1";
        Flux<Book> bookFlux = bookDao.findByName(name);
        StepVerifier
                .create(bookFlux)
                .expectNextMatches(book -> name.equals(book.getName()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Неуспешное получение книги по имени. Не существующее имя книги")
    void doesntShouldGetBookByName() {
        Flux<Book> bookFlux = bookDao.findByName("Как я встретил вашу маму");

        StepVerifier
                .create(bookFlux)
                .verifyComplete();
    }

    @Test
    @DisplayName("Неуспешное получение книги по ид. Не существующий ид книги")
    void doesntShouldGetBookById() {
        Mono<Book> bookFlux = bookDao.findById("1234");

        StepVerifier
                .create(bookFlux)
                .verifyComplete();
    }
}