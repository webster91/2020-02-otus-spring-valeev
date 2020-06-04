package ru.otus.valeev.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Book.COLLECTION_NAME)
public class Book {

    public static final String COLLECTION_NAME = "books";

    @Id
    private String id;
    private String name;
    private Author author;
    private Genre genre;
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}