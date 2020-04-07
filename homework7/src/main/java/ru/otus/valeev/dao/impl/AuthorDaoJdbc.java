package ru.otus.valeev.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("SELECT ID, NAME FROM authors", new AuthorMapper());
    }

    @Override
    public Author getAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return DataAccessUtils.singleResult(jdbc.query("SELECT ID, NAME FROM authors WHERE id = :id", params, new AuthorMapper()));
    }

    @Override
    public Author getAuthorByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return DataAccessUtils.singleResult(jdbc.query("SELECT ID, NAME FROM authors WHERE name = :name", params, new AuthorMapper()));
    }

    @Override
    public Author saveAuthorByName(String name) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        jdbc.update("INSERT INTO authors(`name`) VALUES (:name)", params, generatedKeyHolder);
        if (generatedKeyHolder.getKey() != null) {
            return getAuthorById(generatedKeyHolder.getKey().longValue());
        } else {
            return null;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return Author.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
