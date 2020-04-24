package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
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
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public Book findById(String bookId) {
        return bookDao.findById(bookId).orElse(null);
    }

    @Override
    @Transactional
    public Book save(String bookName, String authorName, String genreName) {
        return bookDao.save(Book.builder()
                .author(authorService.saveAuthorByName(authorName))
                .genre(genreService.saveGenreByName(genreName))
                .name(bookName)
                .build());
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book update(String bookId, String authorName, String genreName) {
        Book book = bookDao.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        } else {
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
    public List<Comment> findCommentsByBookId(String bookId) {
        return bookDao.findById(bookId)
                .map(Book::getComments)
                .orElse(null);
    }
}