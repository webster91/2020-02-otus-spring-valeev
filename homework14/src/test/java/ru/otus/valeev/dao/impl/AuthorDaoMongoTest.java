package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.domain.AuthorMongo;
import ru.otus.valeev.repository.AuthorMongoRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDao")
@SpringBootTest
class AuthorDaoMongoTest {

    @Autowired
    private AuthorMongoRepository authorDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Получение всех авторов")
    void shouldGetAllAuthors() {
        List<AuthorMongo> authors = mongoTemplate.findAll(AuthorMongo.class);
        List<AuthorMongo> authorsExcepted = authorDao.findAll();
        assertThat(authors.size()).isEqualTo(authorsExcepted.size());
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя null")
    void doesntShouldGetAuthorByNameNull() {
        AuthorMongo author = authorDao.findByName(null);
        assertThat(author).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя не известно")
    void doesntShouldGetAuthorByNameEmpty() {
        AuthorMongo authorExcepted = authorDao.findByName("Ignatik");
        assertThat(authorExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени.")
    void shouldGetAuthorByNameEmpty() {
        AuthorMongo author = new AuthorMongo("Vasiliy");
        mongoTemplate.save(author);
        AuthorMongo authorExcepted = authorDao.findByName(author.getName());
        assertThat(authorExcepted).isEqualTo(author);
    }
}