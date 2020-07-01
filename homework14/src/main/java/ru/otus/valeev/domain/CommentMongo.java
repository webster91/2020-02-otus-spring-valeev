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
@Document(collection = CommentMongo.COLLECTION_NAME)
public class CommentMongo {

    public static final String COLLECTION_NAME = "comments";

    @Id
    private long id;
    @Field
    private String text;
    @DBRef
    @ToString.Exclude
    private BookMongo book;

    public CommentMongo(String text, BookMongo book) {
        this.text = text;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentMongo that = (CommentMongo) o;

        if (id != that.id) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + text.hashCode();
        return result;
    }
}
