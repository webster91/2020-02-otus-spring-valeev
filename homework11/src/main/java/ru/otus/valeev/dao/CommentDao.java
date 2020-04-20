package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.valeev.domain.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {
}
