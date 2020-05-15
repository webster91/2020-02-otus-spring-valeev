package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = Book.COLLECTION_NAME)
public class Book {

    public static final String COLLECTION_NAME = "books";

    @Id
    private String id;
    @Field
    @NotBlank
    private String name;
    @DBRef
    @Valid
    @NotNull
    private Author author;
    @DBRef
    @Valid
    @NotNull
    private Genre genre;
    @DBRef(lazy = true)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}