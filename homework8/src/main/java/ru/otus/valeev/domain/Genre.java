package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = Genre.COLLECTION_NAME)
public class Genre {

    public static final String COLLECTION_NAME = "genres";

    @Id
    private String id;
    @Field
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
