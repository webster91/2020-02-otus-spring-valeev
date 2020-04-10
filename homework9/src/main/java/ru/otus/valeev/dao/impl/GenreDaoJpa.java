package ru.otus.valeev.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    public Genre getGenreByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g WHERE g.name = :name", Genre.class);
        query.setParameter("name", name);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public Genre getGenreById(long id) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g WHERE g.id = :id", Genre.class);
        query.setParameter("id", id);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Genre saveGenreByName(String name) {
        return entityManager.merge(
                Genre.builder()
                        .name(name)
                        .build());
    }
}
