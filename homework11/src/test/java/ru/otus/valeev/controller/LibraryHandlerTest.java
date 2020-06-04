package ru.otus.valeev.controller;

import com.github.cloudyrock.mongock.SpringMongock;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.dto.BookDto;
import ru.otus.valeev.utils.BookMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryHandlerTest {

    @Autowired
    private RouterFunction<?> routerFunction;
    @Autowired
    private SpringMongock mongobee;

    @MockBean
    private BookDao bookDao;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }

    @Test
    @DisplayName("Получение всех книг")
    void shouldGetAllBooks() {
        Book book = new Book();
        book.setName("Hey");

        Flux<Book> flux = Flux.just(book, book, book);

        given(bookDao.findAll()).willReturn(flux);

        client.get()
                .uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class).hasSize(3);
    }

    @Test
    @DisplayName("Создание книги")
    void shouldCreateBook() {
        BookDto book = new BookDto();
        book.setName("Hey");

        given(bookDao.save(any())).willReturn(Mono.just(BookMapper.INSTANCE.toBook(book)));

        client.post()
                .uri("/api/book")
                .body(Mono.just(book), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(bookDto -> Assert.isNonEmpty(bookDto.getId()));
    }

    @Test
    @DisplayName("Поиск книги по ид")
    void shouldFindById() {
        Book book = new Book();
        book.setName("Hey");
        book.setId("12345");

        given(bookDao.findById(anyString())).willReturn(Mono.just(book));

        client.get()
                .uri("/api/book/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(bookDto -> assertThat(book.getId()).isEqualTo(bookDto.getId()));
    }

    @Test
    @DisplayName("Обновление книги")
    void shouldUpdateBook() {
        BookDto book = new BookDto();
        book.setName("Hey");

        given(bookDao.save(any())).willReturn(Mono.just(BookMapper.INSTANCE.toBook(book)));

        client.patch()
                .uri("/api/book")
                .body(Mono.just(book), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(bookDto -> Assert.isNonEmpty(bookDto.getId()));
    }

    @Test
    @DisplayName("Удаление книги")
    void shouldDeleteBook() {
        Book book = new Book();
        book.setName("Hey");
        book.setId("12345");

        given(bookDao.deleteById(anyString())).willReturn(Mono.empty());

        client.delete()
                .uri("/api/book/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(id -> assertThat(book.getId()).isEqualTo(id));
    }
}