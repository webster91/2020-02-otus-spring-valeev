package ru.otus.valeev.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.valeev.domain.CommentMongo;

import java.util.List;

public interface CommentMongoRepository extends MongoRepository<CommentMongo, Long> {
    List<CommentMongo> deleteCommentByBook_Id(Long name);
}
