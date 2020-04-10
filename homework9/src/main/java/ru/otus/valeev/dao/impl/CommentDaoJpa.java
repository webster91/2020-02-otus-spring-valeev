package ru.otus.valeev.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment getCommentById(long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        if (comment.getId() > 0) {
            return entityManager.merge(comment);
        } else {
            entityManager.persist(comment);
            return comment;
        }
    }

    @Override
    public List<Comment> getCommentsByBookId(long id) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c " +
                        "FROM Comment c " +
                        "WHERE c.book_id = :book_id",
                Comment.class);
        query.setParameter("book_id", id);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean deleteCommentById(long id) {
        Query query = entityManager.createQuery("DELETE FROM Comment c WHERE c.id = :id");
        query.setParameter("id", id);
        int rs = query.executeUpdate();
        return rs == 1;
    }
}
