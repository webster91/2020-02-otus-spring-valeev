package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.domain.*;
import ru.otus.valeev.repository.BookJpaRepository;
import ru.otus.valeev.repository.BookMongoRepository;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMongoRepository bookMongoRepository;
    private final BookJpaRepository bookJpaRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<BookMongo> findAll() {
        return bookMongoRepository.findAll();
    }

    @Override
    public List<BookJpa> findAllJpa() {
        return bookJpaRepository.findAll();
    }

    @Override
    public BookMongo findByName(String name) {
        return bookMongoRepository.findByName(name);
    }

    @Override
    public BookMongo findById(Long bookId) {
        return bookMongoRepository.findById(bookId).orElse(null);
    }

    @Override
    @Transactional
    public BookMongo save(String bookName, String authorName, String genreName) {
        AuthorMongo authorMongo = authorService.saveAuthorByName(authorName);
        GenreMongo genreMongo = genreService.saveGenreByName(genreName);
        return bookMongoRepository.save(new BookMongo(bookName, authorMongo, genreMongo));
    }

    @Override
    @Transactional
    public BookMongo save(BookMongo bookMongo) {
        return bookMongoRepository.save(bookMongo);
    }

    @Override
    @Transactional
    public BookMongo update(Long bookId, String authorName, String genreName) {
        BookMongo bookMongo = bookMongoRepository.findById(bookId).orElse(null);
        if (bookMongo == null) {
            return null;
        } else {
            bookMongo.setAuthor(authorService.saveAuthorByName(authorName));
            bookMongo.setGenre(genreService.saveGenreByName(genreName));
            return bookMongoRepository.save(bookMongo);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long bookId) {
        if (bookMongoRepository.existsById(bookId)) {
            bookMongoRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentMongo> findCommentsByBookId(Long bookId) {
        return bookMongoRepository.findById(bookId)
                .map(BookMongo::getComments)
                .orElse(null);
    }
}