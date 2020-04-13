package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookDao")
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoTest {
    private static final int EXPECTED_BOOK_COUNT = 6;
    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Успешное получение всех книг")
    void shouldGetAllBooks() {
        List<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("Успешное получение книги по имени")
    void shouldGetBookByName() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Book exceptedBook = bookDao.findByName(book.getName());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное получение книги по имени. Не существующее имя книги")
    void doesntShouldGetBookByName() {
        Book exceptedBook = bookDao.findByName("Как я встретил вашу маму");
        assertThat(exceptedBook).isNull();
    }

    @Test
    @DisplayName("Успешное получение книги по ид")
    void shouldGetBookById() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Book exceptedBook = bookDao.findById(book.getId());
        assertThat(exceptedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Ошибочное получение книги по ид. Не существующий ид книги")
    void doesntShouldGetBookById() {
        Book exceptedBook = bookDao.findById(45);
        assertThat(exceptedBook).isNull();
    }

    @Test
    @DisplayName("Успешное сохранение книги")
    void shouldSaveBook() {
        Book book = Book.builder()
                .name("Zero dawn")
                .author(Author.builder()
                        .name("Ignat")
                        .build())
                .genre(Genre.builder()
                        .name("War")
                        .build())
                .build();
        Book exceptedBook = bookDao.save(book);
        Book savedBook = em.find(Book.class, exceptedBook.getId());
        assertThat(exceptedBook).isEqualTo(savedBook);
    }

    @Test
    @DisplayName("Успешное обновление книги")
    void shouldUpdateBook() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        book.setAuthor(new Author(7, "Igor"));
        Book updatedBook = bookDao.save(book);

        assertThat(book).isEqualTo(updatedBook);
    }

    @Test
    @DisplayName("Успешное удаление книги")
    void shouldDeleteByName() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Book rs = bookDao.delete(book);

        Book deletedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(rs).isEqualTo(book);
        assertThat(deletedBook).isNull();
    }

    @Test
    @DisplayName("Ошибочное удаление книги. Ид книги не существует")
    void doesntShouldDeleteByName() {
        Book rs = bookDao.delete(new Book(48, null, null, null, null));
        assertThat(rs).isNull();
    }

    @Test
    @DisplayName("Ошибочное удаление книги. Ид книги null")
    void doesntShouldDeleteByNameNull() {
        Book rs = bookDao.delete(new Book(0, null, null, null, null));
        assertThat(rs).isNull();
    }

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.dao")
    static class BookDaoConfiguration {
    }
}