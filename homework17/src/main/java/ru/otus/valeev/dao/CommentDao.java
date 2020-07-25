package ru.otus.valeev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.Comment;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment, String> {
    List<Comment> deleteCommentByBook_Id(String name);
}
