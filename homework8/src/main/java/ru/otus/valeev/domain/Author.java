package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = Author.COLLECTION_NAME)
public class Author {

    public static final String COLLECTION_NAME = "authors";

    @Id
    private String id;
    @Field
    private String name;

    public Author(String name) {
        this.name = name;
    }

}