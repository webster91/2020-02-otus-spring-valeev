package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.dto.BookDto;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.VangaService;
import ru.otus.valeev.utils.BookMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class LibraryController {
    private final BookService bookService;
    private final VangaService vangaService;

    @PostMapping("api/book")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
        Book book = BookMapper.INSTANCE.toBook(bookDto);
        log.info("Сохранение книги: " + book);
        Book exceptedBook = bookService.save(book);
        BookDto exceptedBookDto = BookMapper.INSTANCE.toBookDto(exceptedBook);
        return ResponseEntity.ok(exceptedBookDto);
    }

    @GetMapping({"/api/book"})
    public ResponseEntity<List<BookDto>> getBooks() {
        log.info("Получение всех книг");
        List<BookDto> books = bookService.findAll().stream()
                .map(BookMapper.INSTANCE::toBookDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/book/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable("bookId") String bookId) {
        log.info("Получение книги с ид: " + bookId);
        Book book = bookService.findById(bookId);
        BookDto bookDto = BookMapper.INSTANCE.toBookDto(book);
        return ResponseEntity.ok(bookDto);
    }

    @PatchMapping("/api/book")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody BookDto bookDto) {
        log.info("Обновление книги с ид: " + bookDto.getId());
        Book book = BookMapper.INSTANCE.toBook(bookDto);
        Book bookResult = bookService.save(book);
        return ResponseEntity.ok(bookResult);
    }

    @DeleteMapping("/api/book/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") String bookId) {
        log.info("Удаление книги с ид: " + bookId);
        bookService.deleteById(bookId);
        return ResponseEntity.ok(bookId);
    }


    @GetMapping("/api/predicate/{bookId}")
    public ResponseEntity<String> getRandomPrediction(@PathVariable("bookId") String bookId) {
        String predicate = vangaService.getPredicate(bookId);
        return ResponseEntity.ok(predicate);
    }
}
