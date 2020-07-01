package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = AuthorMongo.COLLECTION_NAME)
public class AuthorMongo {

    public static final String COLLECTION_NAME = "authors";

    @Id
    private long id;
    @Field
    private String name;

    public AuthorMongo(String name) {
        this.name = name;
    }

}