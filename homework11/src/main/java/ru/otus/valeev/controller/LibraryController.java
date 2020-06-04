package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.dto.BookDto;
import ru.otus.valeev.utils.BookMapper;

import javax.validation.Valid;

/**
 * Классический Flux
 */
//@RestController
@AllArgsConstructor
@Slf4j
public class LibraryController {
    private final BookDao bookDao;

    @PostMapping("api/book")
    public Mono<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
        Book book = BookMapper.INSTANCE.toBook(bookDto);
        log.info("Сохранение книги: " + book);
        return bookDao.save(book)
                .map(BookMapper.INSTANCE::toBookDto);
    }

    @GetMapping({"/api/book"})
    public Flux<BookDto> getBooks() {
        log.info("Получение всех книг");
        return bookDao.findAll()
                .map(BookMapper.INSTANCE::toBookDto);
    }

    @GetMapping("/api/book/{bookId}")
    public Mono<BookDto> getBook(@PathVariable("bookId") String bookId) {
        log.info("Получение книги с ид: " + bookId);
        return bookDao.findById(bookId)
                .map(BookMapper.INSTANCE::toBookDto);
    }

    @PatchMapping("/api/book")
    public Mono<BookDto> updateBook(@Valid @RequestBody BookDto bookDto) {
        log.info("Обновление книги с ид: " + bookDto.getId());
        Book book = BookMapper.INSTANCE.toBook(bookDto);
        return bookDao.save(book)
                .map(BookMapper.INSTANCE::toBookDto);
    }

    @DeleteMapping("/api/book/{bookId}")
    public Mono<String> deleteBook(@PathVariable("bookId") String bookId) {
        log.info("Удаление книги с ид: " + bookId);
        return bookDao.deleteById(bookId)
                .then(Mono.just(bookId));
    }
}