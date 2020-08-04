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
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookDao")
@DataMongoTest
@Import(MongoConfig.class)
class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Успешное получение всех книг")
    void shouldGetAllBooks() {
        List<Book> books = bookDao.findAll();
        List<Book> booksExcepted = mongoTemplate.findAll(Book.class);
        assertThat(books.size()).isEqualTo(booksExcepted.size());
    }

    @Test
    @DisplayName("Успешное получение книги по имени")
    void shouldGetBookByName() {
        Book book = bookDao.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        Book exceptedBook = bookDao.findByName(book.getName());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Неуспешное получение книги по имени. Не существующее имя книги")
    void doesntShouldGetBookByName() {
        Book exceptedBook = bookDao.findByName("Как я встретил вашу маму");
        assertThat(exceptedBook).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение книги по ид. Не существующий ид книги")
    void doesntShouldGetBookById() {
        Book exceptedBook = bookDao.findById("45").orElse(null);
        assertThat(exceptedBook).isNull();
    }
}