package ru.otus.valeev.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre getGenreByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return DataAccessUtils.singleResult(jdbc.query("SELECT id, name FROM genres WHERE name = :name", params, new GenreMapper()));
    }

    @Override
    public Genre getGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return DataAccessUtils.singleResult(jdbc.query("SELECT id, name FROM genres WHERE id = :id", params, new GenreMapper()));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT id, name FROM genres", new GenreMapper());
    }

    @Override
    public Genre saveGenreByName(String name) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        jdbc.update("INSERT INTO genres(`name`) VALUES (:name)", params, generatedKeyHolder);
        if (generatedKeyHolder.getKey() != null) {
            return getGenreById(generatedKeyHolder.getKey().longValue());
        } else {
            return null;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}