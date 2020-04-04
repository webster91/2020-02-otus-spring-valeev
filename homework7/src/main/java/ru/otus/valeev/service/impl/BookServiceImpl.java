package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<Book> getBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.getBookByName(name);
    }

    @Override
    public Book saveBook(String bookName, String authorName, String genreName) {
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            throw new RuntimeException("Ошибка во входных данных при создании книги");
        }

        Book book = bookDao.getBookByName(bookName);
        if (book != null) {
            if (authorName.equals(book.getAuthor().getName()) && genreName.equals(book.getGenre().getName())) {
                throw new RuntimeException("Данная книга уже существует");
            } else {
                throw new RuntimeException("Данная книга уже существует с другими парам.");
            }
        }

        Author author = authorService.saveAuthorByName(authorName);
        Genre genre = genreService.saveGenreByName(genreName);
        return bookDao.saveBook(Book.builder()
                .author(author)
                .genre(genre)
                .name(bookName)
                .build());
    }

    @Override
    public Book updateBook(String bookName, String authorName, String genreName) {
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            throw new RuntimeException("Ошибка во входных данных при создании книги");
        }

        Book book = bookDao.getBookByName(bookName);
        if (book == null) {
            return null;
        } else {
            Author author = authorService.saveAuthorByName(authorName);
            Genre genre = genreService.saveGenreByName(genreName);
            return bookDao.updateBook(Book.builder()
                    .author(author)
                    .genre(genre)
                    .name(bookName)
                    .build());
        }
    }

    @Override
    public boolean deleteBook(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            throw new RuntimeException("Ошибка во входных данных при удалении книги");
        }
        return bookDao.deleteByName(bookName);
    }
}
