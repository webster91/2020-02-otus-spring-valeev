package ru.otus.valeev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = GenreMongo.COLLECTION_NAME)
public class GenreMongo {

    public static final String COLLECTION_NAME = "genres";

    @Id
    private long id;
    @Field
    private String name;

    public GenreMongo(String name) {
        this.name = name;
    }
}
