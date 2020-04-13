package ru.otus.valeev.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreDaoJpa implements GenreDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Genre findByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g WHERE g.name = :name", Genre.class);
        query.setParameter("name", name);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public Genre findById(long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() > 0) {
            return entityManager.merge(genre);
        } else {
            entityManager.persist(genre);
            return genre;
        }
    }
}
