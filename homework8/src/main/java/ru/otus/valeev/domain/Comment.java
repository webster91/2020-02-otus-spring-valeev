package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@Document(collection = Comment.COLLECTION_NAME)
public class Comment {

    public static final String COLLECTION_NAME = "comments";

    @Id
    private String id;
    @Field
    private String text;
    @DBRef
    @ToString.Exclude
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
