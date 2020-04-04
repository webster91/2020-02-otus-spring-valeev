package ru.otus.valeev.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.dao.GenreDao;
import ru.otus.valeev.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Log4j2
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("SELECT id, name, author_id, genre_id from books", new BookMapper());
    }

    @Override
    public Book getBookByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return DataAccessUtils.singleResult(jdbc.query("SELECT id, name, author_id, genre_id FROM books WHERE name = :name", params, new BookMapper()));
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return DataAccessUtils.singleResult(jdbc.query("SELECT id, name, author_id, genre_id FROM books WHERE id = :id", params, new BookMapper()));
    }

    @Override
    public Book saveBook(Book book) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor() != null ? book.getAuthor().getId() : null);
        params.addValue("genre_id", book.getGenre() != null ? book.getGenre().getId() : null);
        jdbc.update("INSERT INTO books(`name`, `author_id`, `genre_id`) VALUES (:name, :author_id, :genre_id)", params, generatedKeyHolder);
        if (generatedKeyHolder.getKey() != null) {
            return getBookById(generatedKeyHolder.getKey().longValue());
        } else {
            return null;
        }
    }

    @Override
    public Book updateBook(Book book) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor() != null ? book.getAuthor().getId() : null);
        params.addValue("genre_id", book.getGenre() != null ? book.getGenre().getId() : null);
        jdbc.update("UPDATE books SET author_id = :author_id, genre_id = :genre_id WHERE name = :name", params, generatedKeyHolder);
        if (generatedKeyHolder.getKey() != null) {
            return getBookById(generatedKeyHolder.getKey().longValue());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        int rs = jdbc.update("DELETE FROM books WHERE name = :name", params);
        return rs == 1;
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("author_id");
            long genreId = resultSet.getLong("genre_id");
            return new Book(id, name, authorDao.getAuthorById(authorId), genreDao.getGenreById(genreId));
        }
    }
}
