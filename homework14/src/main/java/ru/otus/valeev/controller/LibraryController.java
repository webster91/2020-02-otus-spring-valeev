package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.otus.valeev.domain.*;
import ru.otus.valeev.service.*;

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
    private final MigrationService migrationService;

    @ShellMethod(value = "Get all books", key = {"aa", "allAuthors"})
    public List<AuthorMongo> getAuthors() {
        return authorService.allAuthors();
    }

    @ShellMethod(value = "Get all books", key = {"ab", "allBooks"})
    public List<BookMongo> getBooks() {
        return bookService.findAll();
    }

    @ShellMethod(value = "Get all books", key = {"ag", "allGenres"})
    public List<GenreMongo> getGenres() {
        return genreService.allGenres();
    }

    @ShellMethod(value = "Create/Save book", key = {"cb", "sb", "createBook", "saveBook"})
    public BookMongo saveBook(@ShellOption() String bookName,
                              @ShellOption() String authorName,
                              @ShellOption() String genreName) {
        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            consoleService.sendMessage("Ошибка во входных данных при создании книги");
            return null;
        }
        return bookService.save(bookName, authorName, genreName);
    }

    @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
    public String updateBook(@ShellOption() Long bookId,
                             @ShellOption() String authorName,
                             @ShellOption() String genreName) {
        if (bookId == null || StringUtils.isBlank(authorName) || StringUtils.isBlank(genreName)) {
            consoleService.sendMessage("Ошибка во входных данных для изменении книги");
            return null;
        }
        BookMongo bookMongo = bookService.update(bookId, authorName, genreName);
        if (bookMongo != null) {
            return bookMongo.toString();
        } else {
            return "Ошибка при изменение книги с ид: " + bookId;
        }
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBook"})
    public void deleteBook(@ShellOption() Long bookId) {
        String message;
        if (bookId == null) {
            message = "Ошибка во входных данных для удаления книги";
            consoleService.sendMessage(message);
            return;
        }
        if (bookService.deleteById(bookId)) {
            message = "Успешное удаление книги с ид: " + bookId;
        } else {
            message = "Ошибка при удаление книги с ид: " + bookId;
        }
        consoleService.sendMessage(message);
    }

    @ShellMethod(value = "Add comment for book", key = {"sc", "addCom", "saveCom"})
    public CommentMongo saveComment(@ShellOption() Long bookId,
                                    @ShellOption() String comment) {
        String message;
        if (bookId == null || StringUtils.isBlank(comment)) {
            message = "Ошибка во входных данных при добавлении коментария";
            consoleService.sendMessage(message);
            return null;
        }
        return commentService.save(bookId, comment);
    }

    @ShellMethod(value = "Find comments by book", key = {"fc", "findCom"})
    public List<CommentMongo> findCommentByBookId(@ShellOption() Long bookId) {
        if (bookId == null) {
            consoleService.sendMessage("Ошибка во входных данных при поиске коментария");
            return null;
        }
        List<CommentMongo> commentMongos = bookService.findCommentsByBookId(bookId);
        if (CollectionUtils.isEmpty(commentMongos)) {
            consoleService.sendMessage("Не найдены комментарии у книги с ид: " + bookId);
            return null;
        } else {
            return commentMongos;
        }
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "delCom"})
    public void deleteComment(@ShellOption() Long commentId) {
        String message;
        if (commentId == null) {
            message = "Ошибка во входных данных при удалении коментария";
            consoleService.sendMessage(message);
            return;
        }
        if (commentService.deleteById(commentId)) {
            message = "Успешное удаление коментария с ид: " + commentId;
        } else {
            message = "Ошибка при удаление коментария с ид: " + commentId;
        }
        consoleService.sendMessage(message);
    }

    @ShellMethod(value = "Start migration", key = {"m", "migration"})
    public void startMigrationJob() {
        migrationService.executeMigration();
    }

    @ShellMethod(value = "Get all books from Jpa repository", key = {"abj", "allBooksJpa"})
    public List<BookJpa> getBooksJpa() {
        return bookService.findAllJpa();
    }
}