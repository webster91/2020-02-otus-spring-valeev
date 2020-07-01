package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.repository.BookMongoRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("bookMongoRepository")
@SpringBootTest
class BookDaoTest {

    @Autowired
    private BookMongoRepository bookMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Успешное получение всех книг")
    void shouldGetAllBooks() {
        List<BookMongo> books = bookMongoRepository.findAll();
        List<BookMongo> booksExcepted = mongoTemplate.findAll(BookMongo.class);
        assertThat(books.size()).isEqualTo(booksExcepted.size());
    }

    @Test
    @DisplayName("Успешное получение книги по имени")
    void shouldGetBookByName() {
        BookMongo book = bookMongoRepository.findAll().stream().findFirst().orElseThrow();
        BookMongo exceptedBook = bookMongoRepository.findByName(book.getName());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Неуспешное получение книги по имени. Не существующее имя книги")
    void doesntShouldGetBookByName() {
        BookMongo exceptedBook = bookMongoRepository.findByName("Как я встретил вашу маму");
        assertThat(exceptedBook).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение книги по ид. Не существующий ид книги")
    void doesntShouldGetBookById() {
        BookMongo exceptedBook = bookMongoRepository.findById(45L).orElse(null);
        assertThat(exceptedBook).isNull();
    }
}