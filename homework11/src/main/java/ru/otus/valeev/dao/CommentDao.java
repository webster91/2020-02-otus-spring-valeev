package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentDao extends ReactiveMongoRepository<Comment, String> {
    List<Comment> deleteCommentByBook_Id(String name);
}
