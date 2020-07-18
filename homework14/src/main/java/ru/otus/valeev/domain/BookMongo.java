package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = BookMongo.COLLECTION_NAME)
public class BookMongo {

    public static final String COLLECTION_NAME = "books";

    @Id
    private long id;
    @Field
    private String name;
    @DBRef
    private AuthorMongo author;
    @DBRef
    private GenreMongo genre;
    @ToString.Exclude
    private List<CommentMongo> comments = new ArrayList<>();

    public BookMongo(String name, AuthorMongo author, GenreMongo genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}