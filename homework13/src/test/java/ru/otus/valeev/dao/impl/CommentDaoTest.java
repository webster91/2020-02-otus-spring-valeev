package ru.otus.valeev.dao.impl;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentDao")
@DataMongoTest
@Import(MongoConfig.class)
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongobee;

    @BeforeEach
    void init() {
        mongobee.execute();
    }

    @Test
    @DisplayName("Получение всех комментарий")
    void shouldGetAllGenres() {
        List<Comment> comments = mongoTemplate.findAll(Comment.class);
        List<Comment> commentExcepted = commentDao.findAll();
        assertThat(comments.size()).isEqualTo(commentExcepted.size());
    }


    @Test
    @DisplayName("Удаление комментария по ид книги")
    void shouldDeleteCommentById() {
        Book book = mongoTemplate.findAll(Book.class).stream()
                .findFirst()
                .orElseThrow();
        commentDao.save(new Comment(null, "Comment", book));
        commentDao.deleteById(book.getId());
        Book bookExcepted = mongoTemplate.findById(book.getId(), Book.class);
        assertThat(bookExcepted).isNotNull()
                .matches(s -> CollectionUtils.isEmpty(s.getComments()));
    }

    @Test
    @DisplayName("Неуспешное удаление комментарий по ид книги. Id книги неизвестный")
    void doesntShouldDeleteCommentById() {
        List<Comment> comments = commentDao.deleteCommentByBook_Id("123123144");
        assertThat(comments).isEmpty();
    }
}