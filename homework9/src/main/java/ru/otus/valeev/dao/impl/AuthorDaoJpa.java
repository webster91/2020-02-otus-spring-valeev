package ru.otus.valeev.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorDaoJpa implements AuthorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author getAuthorById(long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author getAuthorByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class);
        query.setParameter("name", name);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    @Transactional
    public Author saveAuthorByName(String name) {
        Author author = Author.builder()
                .name(name)
                .build();
        entityManager.persist(author);
        return author;
    }
}
