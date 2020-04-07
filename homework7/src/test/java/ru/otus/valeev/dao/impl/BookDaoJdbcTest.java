package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PersonDao")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final int EXPECTED_BOOK_COUNT = 6;
    private static final String DEFAULT_BOOK_NAME = "Как изучить спринг за 3 часа";
    private static final Author DEFAULT_AUTHOR = new Author(2, "Пушкин");
    private static final Author DEFAULT_ANOTHER_AUTHOR = new Author(1, "Мой сосед");
    private static final Genre DEFAULT_GENRE = new Genre(2, "Саморазвитие");
    private static final Book DEFAULT_BOOK = new Book(7, DEFAULT_BOOK_NAME, DEFAULT_AUTHOR, DEFAULT_GENRE);

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("Успешное получение всех книг")
    void shouldGetAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        assertThat(books.size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("Успешное получение книги по имени")
    void shouldGetBookByName() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);

        Book exceptedBook = bookDao.getBookByName(DEFAULT_BOOK.getName());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное получение книги по имени. Не существующее имя книги")
    void doesntShouldGetBookByName() {
        Book exceptedBook = bookDao.getBookByName("Как я встретил вашу маму");
        assertThat(exceptedBook).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение книги по ид")
    void shouldGetBookById() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);

        Book exceptedBook = bookDao.getBookById(book.getId());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное получение книги по ид. Не существующий ид книги")
    void doesntShouldGetBookById() {
        Book exceptedBook = bookDao.getBookById(45);
        assertThat(exceptedBook).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное сохранение книги")
    void shouldSaveBook() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);
        Book exceptedBook = bookDao.getBookById(book.getId());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное сохранение книги. Ид книги 0")
    void doesntShouldSaveBook() {
        DEFAULT_BOOK.setId(0);

        bookDao.saveBook(DEFAULT_BOOK);
        Book exceptedBook = bookDao.getBookById(DEFAULT_BOOK.getId());
        assertThat(exceptedBook).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное обновление книги")
    void shouldUpdateBook() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);
        book.setAuthor(DEFAULT_ANOTHER_AUTHOR);

        bookDao.updateBook(book);
        Book exceptedBook = bookDao.getBookById(book.getId());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное обновление книги. Имя книги null")
    void doesntShouldUpdateBook() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);
        book.setAuthor(DEFAULT_ANOTHER_AUTHOR);
        book.setName(null);

        Book exceptedBook = bookDao.updateBook(book);
        assertThat(exceptedBook).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное удаление книги")
    void shouldDeleteByName() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);

        boolean rs = bookDao.deleteByName(book.getName());
        assertThat(rs).isEqualTo(true);
    }

    @Test
    @DisplayName("Ошибочное удаление книги. Имя книги null")
    void doesntShouldDeleteByName() {
        Book book = bookDao.saveBook(DEFAULT_BOOK);
        String bookName = "doesntShouldDeleteByName";
        book.setName(null);
        boolean rs = bookDao.deleteByName(bookName);
        assertThat(rs).isEqualTo(false);
    }

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.dao")
    static class BookDaoConfiguration {
    }
}