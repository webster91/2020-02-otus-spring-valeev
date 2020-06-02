package ru.otus.valeev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;

import java.util.Optional;

@Data
@NoArgsConstructor
public class BookEditDTO {
    private String id;
    private String name;
    private String authorName;
    private String genreName;

    public BookEditDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.authorName = Optional.ofNullable(book.getAuthor())
                .map(Author::getName)
                .orElse(null);
        this.genreName = Optional.ofNullable(book.getGenre())
                .map(Genre::getName)
                .orElse(null);
    }
}
