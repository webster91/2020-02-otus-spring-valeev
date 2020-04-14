package ru.otus.valeev.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("vitaliy");
        TypedQuery<Book> query = entityManager.createQuery("SELECT b " +
                "FROM Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book findByName(String name) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("allJoins");
        TypedQuery<Book> query = entityManager.createQuery("SELECT b " +
                        "FROM Book b " +
                        "WHERE b.name = :name",
                Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public Book findById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() > 0) {
            return entityManager.merge(book);
        } else {
            entityManager.persist(book);
            return book;
        }
    }

    @Override
    public Book deleteById(Long bookId) {
        Book existing = this.findById(bookId);
        if (existing != null) {
            entityManager.remove(existing);
            return existing;
        } else {
            return null;
        }
    }

    @Override
    public Book delete(Book book) {
        if (book == null) {
            return null;
        }
        return this.deleteById(book.getId());
    }
}
