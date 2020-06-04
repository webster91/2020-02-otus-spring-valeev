package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.dto.BookDto;
import ru.otus.valeev.utils.BookMapper;

@Component
@Slf4j
@AllArgsConstructor
public class LibraryHandler {
    private final BookDao bookDao;

    @NonNull
    public Mono<ServerResponse> getBooks(ServerRequest request) {
        log.info("Получение всех книг");
        Flux<BookDto> books = bookDao.findAll()
                .map(BookMapper.INSTANCE::toBookDto);
        return ServerResponse.ok().body(books, BookDto.class);
    }

    @NonNull
    public Mono<ServerResponse> createBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(BookMapper.INSTANCE::toBook)
                .doOnNext(book -> log.info("Сохранение книги с ид: " + book.getId()))
                .flatMap(book -> bookDao.save(book)
                        .map(BookMapper.INSTANCE::toBookDto))
                .flatMap(bookDto -> ServerResponse.ok().bodyValue(bookDto));
    }

    @NonNull
    public Mono<ServerResponse> updateBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(BookMapper.INSTANCE::toBook)
                .doOnNext(book -> log.info("Обновление книги с ид: " + book.getId()))
                .flatMap(book -> bookDao.save(book)
                        .map(BookMapper.INSTANCE::toBookDto))
                .flatMap(bookDto -> ServerResponse.ok().bodyValue(bookDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> findById(ServerRequest request) {
        return Mono.just(request.pathVariable("bookId"))
                .doOnNext(bookId -> log.info("Поиск книги с ид: " + bookId))
                .flatMap(bookId -> bookDao.findById(request.pathVariable("bookId"))
                        .map(BookMapper.INSTANCE::toBookDto))
                .flatMap(bookDto -> ServerResponse.ok().bodyValue(bookDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> deleteBook(ServerRequest request) {
        return Mono.just(request.pathVariable("bookId"))
                .doOnNext(bookId -> log.info("Удаление книги с ид: " + bookId))
                .flatMap(bookId -> bookDao.deleteById(bookId)
                        .then(ServerResponse.ok().bodyValue(bookId)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
