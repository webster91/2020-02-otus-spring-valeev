package ru.otus.valeev.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment findById(long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() > 0) {
            return entityManager.merge(comment);
        } else {
            entityManager.persist(comment);
            return comment;
        }
    }

    @Override
    public Comment deleteById(long id) {
        Comment comment = this.findById(id);
        if (comment != null) {
            entityManager.remove(comment);
            return comment;
        } else {
            return null;
        }
    }
}
