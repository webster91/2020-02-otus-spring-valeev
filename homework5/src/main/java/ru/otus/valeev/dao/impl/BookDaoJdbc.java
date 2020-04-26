package ru.otus.valeev.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("SELECT b.id, b.name, b.author_id, b.genre_id, a.id, a.name, g.id, g.name " +
                        "FROM books b " +
                        "INNER JOIN authors a ON b.author_id = a.id " +
                        "INNER JOIN genres g ON b.genre_id = g.id ",
                new BookMapper());
    }

    @Override
    public Book getBookByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return DataAccessUtils.singleResult(jdbc.query("SELECT b.id, b.name, b.author_id, b.genre_id, a.id, a.name, g.id, g.name " +
                        "FROM books b " +
                        "INNER JOIN authors a ON b.author_id = a.id " +
                        "INNER JOIN genres g ON b.genre_id = g.id " +
                        "WHERE b.name = :name",
                params, new BookMapper()));
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return DataAccessUtils.singleResult(jdbc.query("SELECT b.id, b.name, b.author_id, b.genre_id, a.id, a.name, g.id, g.name " +
                        "FROM books b " +
                        "INNER JOIN authors a ON b.author_id = a.id " +
                        "INNER JOIN genres g ON b.genre_id = g.id " +
                        "WHERE b.id = :id",
                params, new BookMapper()));
    }

    @Override
    public Book saveBook(Book book) {
        return modifyBook(book, "INSERT INTO books(`name`, `author_id`, `genre_id`) VALUES (:name, :author_id, :genre_id)");
    }

    @Override
    public Book updateBook(Book book) {
        return modifyBook(book, "UPDATE books SET author_id = :author_id, genre_id = :genre_id WHERE name = :name");
    }

    @Override
    public boolean deleteByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        int rs = jdbc.update("DELETE FROM books WHERE name = :name", params);
        return rs == 1;
    }

    private Book modifyBook(Book book, String sql) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor() != null ? book.getAuthor().getId() : null);
        params.addValue("genre_id", book.getGenre() != null ? book.getGenre().getId() : null);
        jdbc.update(sql, params, generatedKeyHolder);
        if (generatedKeyHolder.getKey() != null) {
            return getBookById(generatedKeyHolder.getKey().longValue());
        } else {
            return null;
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("author_id");
            String author_name = resultSet.getString("authors.name");
            long genreId = resultSet.getLong("genre_id");
            String genre_name = resultSet.getString("genres.name");
            return Book.builder()
                    .id(id)
                    .name(name)
                    .author(Author.builder()
                            .id(authorId)
                            .name(author_name)
                            .build())
                    .genre(Genre.builder()
                            .id(genreId)
                            .name(genre_name)
                            .build())
                    .build();
        }
    }
}
