package ru.otus.valeev.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentDao")
@DataJpaTest
@Import(CommentDaoJpa.class)
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
        Comment commentExcepted = commentDao.getCommentById(comment.getId());
        assertThat(commentExcepted).isEqualTo(comment);
    }

    @Test
    @DisplayName("Получение комментария по несуществующему ид")
    void doesntShouldGetComment() {
        Comment commentExcepted = commentDao.getCommentById(54);
        assertThat(commentExcepted).isNull();
    }

    @Test
    @DisplayName("Получение комментарий по ид книги")
    void shouldGetCommentList() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        List<Comment> commentsExcepted = commentDao.getCommentsByBookId(comment.getId());
        assertThat(commentsExcepted.size()).isEqualTo(EXPECTED_COMMENTS_FOR_FIRST_BOOK_COUNT);
    }

    @Test
    @DisplayName("Получение комментарий по несуществующему ид книги")
    void doesntShouldGetCommentList() {
        List<Comment> commentsExcepted = commentDao.getCommentsByBookId(24);
        assertThat(commentsExcepted.size()).isZero();
    }

    @Test
    @DisplayName("Добавление комментария")
    void shouldAddComment() {
        Comment commentTmp = Comment.builder()
                .book_id(1L)
                .comment("Hello")
                .build();
        Comment commentsExcepted = commentDao.addComment(commentTmp);
        Comment comment = em.find(Comment.class, commentsExcepted.getId());
        assertThat(commentsExcepted).isEqualTo(comment);
    }


    @Test
    @DisplayName("Удаление комментария по ид книги")
    void shouldDeleteCommentById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        boolean rs = commentDao.deleteCommentById(comment.getId());
        assertThat(rs).isTrue();
    }
}