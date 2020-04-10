package ru.otus.valeev.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAllBooks() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("vitaliy");
        TypedQuery<Book> query = entityManager.createQuery("SELECT b " +
                "FROM Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book getBookByName(String name) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("vitaliy");
        TypedQuery<Book> query = entityManager.createQuery("SELECT b " +
                        "FROM Book b " +
                        "WHERE b.name = :name",
                Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public Book getBookById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        if (book.getId() > 0) {
            return entityManager.merge(book);
        } else {
            entityManager.persist(book);
            return book;
        }
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        return entityManager.merge(book);
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.name = :name");
        query.setParameter("name", name);
        int rs = query.executeUpdate();
        return rs == 1;
    }
}
