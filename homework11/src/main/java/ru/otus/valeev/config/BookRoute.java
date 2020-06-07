package ru.otus.valeev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.valeev.controller.LibraryHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Flux в функциональном стиле
 */
@Configuration
public class BookRoute {

    private static final String ENDPOINT = "/api/book";

    @Bean
    public RouterFunction<ServerResponse> bookRouter(LibraryHandler handler) {
        return route(GET(ENDPOINT), handler::getBooks)
                .andRoute(POST(ENDPOINT), handler::createBook)
                .andRoute(PATCH(ENDPOINT), handler::updateBook)
                .andRoute(GET(ENDPOINT + "/{bookId}"), handler::findById)
                .andRoute(DELETE(ENDPOINT + "/{bookId}"), handler::deleteBook);
    }
}
