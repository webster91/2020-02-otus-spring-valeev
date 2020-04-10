package ru.otus.valeev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.valeev.domain.Book;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {

    Book getBookByName(String name);

    Book getBookById(long name);

    List<Book> deleteByName(String name);
}
