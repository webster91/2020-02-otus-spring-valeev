package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {
    Comment findById(long id);

    List<Comment> findAllByBookId(long bookId);

    boolean deleteById(long bookId);
}
