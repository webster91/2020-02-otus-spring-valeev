package ru.otus.valeev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
