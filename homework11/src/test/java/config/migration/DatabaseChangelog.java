package config.migration;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;

import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "001")
@AllArgsConstructor
public class DatabaseChangelog {


    @ChangeSet(order = "002", id = "init DB", author = "valeev")
    public void init(MongoTemplate mongoDatabase) {
        Author ignat = new Author("Ignat");
        Author vasya = new Author("Vasya");
        mongoDatabase.insertAll(Arrays.asList(ignat, vasya));

        Genre fantastic = new Genre("Fantastic");
        Genre education = new Genre("Education");
        mongoDatabase.insertAll(Arrays.asList(fantastic, education));

        List<Book> books = Arrays.asList(
                new Book("Book1", ignat, fantastic),
                new Book("Book2", ignat, education),
                new Book("Book3", vasya, fantastic)
        );
        mongoDatabase.insertAll(books);
    }
}
