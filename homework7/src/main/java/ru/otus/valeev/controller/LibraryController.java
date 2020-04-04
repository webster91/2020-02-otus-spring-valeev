package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@AllArgsConstructor
@Controller
@ShellComponent
public class LibraryController {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    @ShellMethod(value = "Get all books", key = {"aa", "allAuthors"})
    public List<Author> getAuthors() {
        return authorService.allAuthors();
    }

    @ShellMethod(value = "Get all books", key = {"ab", "allBooks"})
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @ShellMethod(value = "Get all books", key = {"ag", "allGenres"})
    public List<Genre> getGenres() {
        return genreService.allGenres();
    }

    @ShellMethod(value = "Create/Save book", key = {"cb", "sb", "createBook", "saveBook"})
    public Book saveBook(@ShellOption String bookName,
                         @ShellOption String authorName,
                         @ShellOption String genreName) {
        return bookService.saveBook(bookName, authorName, genreName);
    }

    @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
    public String updateBook(@ShellOption(defaultValue = "") String bookName,
                             @ShellOption(defaultValue = "") String authorName,
                             @ShellOption(defaultValue = "") String genreName) {
        Book book = bookService.updateBook(bookName, authorName, genreName);
        if (book != null) {
            return book.toString();
        } else {
            return "Ошибка при изменение книги с названием: " + bookName;
        }
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBook"})
    public String deleteBook(@ShellOption(defaultValue = "") String bookName) {
        if (bookService.deleteBook(bookName)) {
            return "Успешное удаление книги с названием: " + bookName;
        } else {
            return "Ошибка при удаление книги с названием: " + bookName;
        }
    }
}