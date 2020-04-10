package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ConsoleService consoleService;

    @Override
    @Transactional
    public List<Book> getBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.getBookByName(name);
    }

    @Override
    @Transactional
    public Book saveBook(String bookName, String authorName, String genreName) {
        Book book = bookDao.getBookByName(bookName);
        if (book != null) {
            String message;
            if (authorName.equals(book.getAuthor().getName()) && genreName.equals(book.getGenre().getName())) {
                message = "Данная книга уже существует";
            } else {
                message = "Данная книга уже существует с другими парам";
            }
            consoleService.sendMessage(message);
            return null;
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
        Book book = bookDao.getBookByName(bookName);
        if (book == null) {
            return null;
        } else {
            Author author = authorService.saveAuthorByName(authorName);
            Genre genre = genreService.saveGenreByName(genreName);
            return bookDao.updateBook(Book.builder()
                    .id(book.getId())
                    .author(author)
                    .genre(genre)
                    .name(bookName)
                    .build());
        }
    }

    @Override
    public boolean deleteBook(String bookName) {
        return bookDao.deleteByName(bookName);
    }
}