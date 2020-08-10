package ru.otus.valeev.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final Random random = new Random();

    @Override
    @HystrixCommand(commandKey = "findAll", fallbackMethod = "fallbackGetBooks")
    public List<Book> findAll() {
        boolean rnd = random.nextBoolean();
        if (rnd) {
            throw new RuntimeException("Не повезло");
        }
        return bookDao.findAll();
    }

    @Override
    @HystrixCommand(commandKey = "findByName", fallbackMethod = "fallbackGetBook")
    public Book findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    @HystrixCommand(commandKey = "findById", fallbackMethod = "fallbackGetBook")
    public Book findById(String bookId) {
        return bookDao.findById(bookId).orElse(null);
    }

    @Override
    @Transactional
    public Book save(String bookName, String authorName, String genreName) {
        Author author = authorService.saveAuthorByName(authorName);
        Genre genre = genreService.saveGenreByName(genreName);
        return bookDao.save(new Book(bookName, author, genre));
    }

    @Override
    @Transactional
    public Book save(Book book) {
        book.setAuthor(authorService.saveAuthorByName(book.getAuthor().getName()));
        book.setGenre(genreService.saveGenreByName(book.getGenre().getName()));
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book update(String bookId, String bookName, String authorName, String genreName) {
        Book book = bookDao.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        } else {
            book.setName(bookName);
            book.setAuthor(authorService.saveAuthorByName(authorName));
            book.setGenre(genreService.saveGenreByName(genreName));
            return bookDao.save(book);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String bookId) {
        if (bookDao.existsById(bookId)) {
            bookDao.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "findCommentsBy", fallbackMethod = "fallbackGetBook")
    public List<Comment> findCommentsByBookId(String bookId) {
        return bookDao.findById(bookId)
                .map(Book::getComments)
                .orElse(null);
    }

    private Book fallbackGetBook(String id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Дорама");

        Author author = new Author();
        author.setId("0");
        author.setName("Сын маминой подруги");

        Book book = new Book();
        book.setId("0");
        book.setName("Книга которую ты не заказывал");
        book.setGenre(genre);
        book.setAuthor(author);
        return book;
    }

    private List<Book> fallbackGetBooks() {
        return Collections.singletonList(fallbackGetBook("0"));
    }
}