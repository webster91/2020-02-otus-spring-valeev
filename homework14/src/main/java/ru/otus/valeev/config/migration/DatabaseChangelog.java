package ru.otus.valeev.config.migration;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.domain.AuthorMongo;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.domain.CommentMongo;
import ru.otus.valeev.domain.GenreMongo;
import ru.otus.valeev.repository.AuthorMongoRepository;
import ru.otus.valeev.repository.BookMongoRepository;
import ru.otus.valeev.repository.CommentMongoRepository;
import ru.otus.valeev.repository.GenreMongoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private final Random random = new Random();

    @ChangeSet(order = "-102", id = "drop_db", author = "valeev")
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "-103", id = "init DB", author = "valeev")
    public void init(MongoTemplate mongoDatabase, AuthorMongoRepository authorMongoRepository, GenreMongoRepository genreMongoRepository, BookMongoRepository bookMongoRepository, CommentMongoRepository commentMongoRepository) {
        AuthorMongo ignat = new AuthorMongo("Ignat");
        AuthorMongo vasya = new AuthorMongo("Vasya");
        authorMongoRepository.saveAll(Arrays.asList(ignat, vasya));

        GenreMongo fantastic = new GenreMongo("Fantastic");
        GenreMongo education = new GenreMongo("Education");
        genreMongoRepository.saveAll(Arrays.asList(fantastic, education));

        List<BookMongo> bookMongos = Arrays.asList(
                new BookMongo("Book1", ignat, fantastic),
                new BookMongo("Book2", ignat, education),
                new BookMongo("Book3", vasya, fantastic)
        );
        bookMongos = bookMongoRepository.saveAll(bookMongos);

        List<CommentMongo> collect = bookMongos.stream()
                .map(book -> {
                    String quantity = random.nextBoolean() ? "best" : "boring";
                    return new CommentMongo(String.format("This is book#%s %s", book.getId(), quantity), book);
                })
                .collect(Collectors.toList());

        commentMongoRepository.saveAll(collect);
    }

}
