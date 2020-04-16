package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.AuthorService;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.CommentService;
import ru.otus.valeev.service.ConsoleService;
import ru.otus.valeev.service.GenreService;

import java.util.List;

@AllArgsConstructor
@Controller
@ShellComponent
public class LibraryController {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final ConsoleService consoleService;

    @ShellMethod(value = "Get all books", key = {"aa", "allAuthors"})
    public List<Author> getAuthors() {
        return authorService.allAuthors();
    }

    @ShellMethod(value = "Get all books", key = {"ab", "allBooks"})
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @ShellMethod(value = "Get all books", key = {"ag", "allGenres"})
    public List<Genre> getGenres() {
        return genreService.allGenres();
    }

    @ShellMethod(value = "Get all comments book", key = {"ac", "acb", "allCom"})
    public List<Comment> allCommentsBook(@ShellOption(defaultValue = "") String bookName) {
        Book book = bookService.findByName(bookName);
        if (book == null || CollectionUtils.isEmpty(book.getComments())) {
            consoleService.sendMessage(String.format("Комментарии для книги %s не найдены", bookName));
            return null;
        }
        return book.getComments();
    }

    @ShellMethod(value = "Create/Save book", key = {"cb", "sb", "createBook", "saveBook"})
    public Book saveBook(@ShellOption(defaultValue = "") String bookName,
                         @ShellOption(defaultValue = "") String authorName,
                         @ShellOption(defaultValue = "") String genreName) {
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            consoleService.sendMessage("Ошибка во входных данных при создании книги");
            return null;
        }
        return bookService.save(bookName, authorName, genreName);
    }

    @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
    public String updateBook(@ShellOption(defaultValue = "") String bookName,
                             @ShellOption(defaultValue = "") String authorName,
                             @ShellOption(defaultValue = "") String genreName) {
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            consoleService.sendMessage("Ошибка во входных данных для изменении книги");
            return null;
        }
        Book book = bookService.update(bookName, authorName, genreName);
        if (book != null) {
            return book.toString();
        } else {
            return "Ошибка при изменение книги с названием: " + bookName;
        }
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBook"})
    public void deleteBook(@ShellOption(defaultValue = "") String bookName) {
        String message;
        if (StringUtils.isBlank(bookName)) {
            message = "Ошибка во входных данных при удалении книги";
            consoleService.sendMessage(message);
            return;
        }
        Book book = bookService.deleteByName(bookName);
        if (book != null) {
            message = "Успешное удаление книги с названием: " + bookName;
        } else {
            message = "Ошибка при удаление книги с названием: " + bookName;
        }
        consoleService.sendMessage(message);
    }

    @ShellMethod(value = "Add comment for book", key = {"sc", "addCom", "saveCom"})
    public Comment saveComment(@ShellOption(defaultValue = "") String bookName,
                               @ShellOption(defaultValue = "") String comment) {
        String message;
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(comment)) {
            message = "Ошибка во входных данных при добавлении коментария";
            consoleService.sendMessage(message);
            return null;
        }
        return commentService.save(bookName, comment);
    }

    @ShellMethod(value = "Find comments by book", key = {"fc", "findCom"})
    public List<Comment> findComment(@ShellOption(defaultValue = "") String bookName) {
        List<Comment> comments = commentService.findCommentsByBookName(bookName);
        if (comments == null || CollectionUtils.isEmpty(comments)) {
            consoleService.sendMessage("Не найдены комментарии у книги : " + bookName);
            return null;
        } else {
            return comments;
        }
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "delCom"})
    public void deleteComment(@ShellOption(defaultValue = "") String commentId) {
        String message;
        if (StringUtils.isBlank(commentId)) {
            message = "Ошибка во входных данных при добавлении коментария";
            consoleService.sendMessage(message);
            return;
        }
        if (commentService.deleteById(NumberUtils.toLong(commentId)) != null) {
            message = "Успешное удаление коментария с ид: " + commentId;
        } else {
            message = "Ошибка при удаление коментария с ид: " + commentId;
        }
        consoleService.sendMessage(message);
    }
}