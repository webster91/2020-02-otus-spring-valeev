package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    public Book findById(Long bookId) {
        return bookDao.findById(bookId);
    }

    @Override
    @Transactional
    public Book save(String bookName, String authorName, String genreName) {
        Author author = authorService.saveAuthorByName(authorName);
        Genre genre = genreService.saveGenreByName(genreName);
        return bookDao.save(Book.builder()
                .author(author)
                .genre(genre)
                .name(bookName)
                .build());
    }

    @Override
    @Transactional
    public Book update(Long bookId, String authorName, String genreName) {
        Book book = bookDao.findById(bookId);
        if (book == null) {
            return null;
        } else {
            Author author = authorService.saveAuthorByName(authorName);
            book.setAuthor(author);
            Genre genre = genreService.saveGenreByName(genreName);
            book.setGenre(genre);
            return bookDao.save(book);
        }
    }

    @Override
    @Transactional
    public Book deleteById(Long bookId) {
        return bookDao.deleteById(bookId);
    }

    @Override
    @Transactional
    public List<Comment> findCommentsByBookId(Long bookId) {
        Book byName = bookDao.findById(bookId);
        if (byName == null) {
            return null;
        } else {
            Hibernate.initialize(byName.getComments());
            return byName.getComments();
        }
    }
}