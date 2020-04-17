package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentDao")
@DataJpaTest
class CommentDaoTest {
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long EXPECTED_COMMENTS_FOR_FIRST_BOOK_COUNT = 2L;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Получение комментария по ид")
    void shouldGetComment() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        Comment commentExcepted = commentDao.findById(comment.getId()).orElse(null);
        assertThat(commentExcepted).isEqualTo(comment);
    }

    @Test
    @DisplayName("Получение комментария по несуществующему ид")
    void doesntShouldGetComment() {
        Comment commentExcepted = commentDao.findById(54L).orElse(null);
        assertThat(commentExcepted).isNull();
    }

    @Test
    @DisplayName("Добавление комментария")
    void shouldAddComment() {
        Comment commentTmp = Comment.builder()
                .comment("Hello")
                .build();
        Comment commentsExcepted = commentDao.save(commentTmp);
        Comment comment = em.find(Comment.class, commentsExcepted.getId());
        assertThat(commentsExcepted).isEqualTo(comment);
    }


    @Test
    @DisplayName("Удаление комментария по ид книги")
    void shouldDeleteCommentById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        commentDao.deleteById(comment.getId());
        Comment commentsExcepted = em.find(Comment.class, comment.getId());
        assertThat(commentsExcepted).isNull();
    }
}