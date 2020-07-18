package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.domain.GenreMongo;
import ru.otus.valeev.repository.GenreMongoRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreDao")
@SpringBootTest
class GenreDaoTest {

    @Autowired
    private GenreMongoRepository genreDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Получение всех жанров")
    void shouldGetAllGenres() {
        List<GenreMongo> genres = mongoTemplate.findAll(GenreMongo.class);
        List<GenreMongo> genresExcepted = genreDao.findAll();
        assertThat(genres.size()).isEqualTo(genresExcepted.size());
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя null")
    void doesntShouldGetGenreByNameNull() {
        GenreMongo genre = genreDao.findByName(null);
        assertThat(genre).isNull();
    }

    @Test
    @DisplayName("Неуспешное получение автора по имени. Имя не известно")
    void doesntShouldGetGenreByName() {
        GenreMongo genreExcepted = genreDao.findByName("Roman4ik");
        assertThat(genreExcepted).isEqualTo(null);
    }

    @Test
    @DisplayName("Успешное получение автора по имени.")
    void shouldGetGenreByName() {
        GenreMongo genre = new GenreMongo("BestEducation");
        mongoTemplate.save(genre);
        GenreMongo genreExcepted = genreDao.findByName(genre.getName());
        assertThat(genreExcepted).isEqualTo(genre);
    }
}