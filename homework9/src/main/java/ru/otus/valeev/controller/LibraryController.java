package ru.otus.valeev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Genre;
import ru.otus.valeev.service.BookService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@Slf4j
public class LibraryController {
    private final BookService bookService;

    @GetMapping({"/", "/book"})
    public String getBooks(Model model) {
        log.info("Получение всех книг");
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable("bookId") String bookId, Model model) {
        log.info("Получение книги с ид: " + bookId);
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        Book book = new Book();
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        model.addAttribute("book", book);
        model.addAttribute("add", true);
        return "edit";
    }

    @PostMapping("/book/add")
    public String saveBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add", true);
            model.addAttribute("book", book);
            model.addAttribute("errorMessage", "Ошибка во входных данных при создании книги");
            return "edit";
        }
        log.info("Сохранение книги: " + book);
        Book exceptedBook = bookService.save(book);
        model.addAttribute("book", exceptedBook);
        return "book";
    }

    @GetMapping("/book/{bookId}/edit")
    public String editBook(@PathVariable("bookId") String bookId, Model model) {
        log.info("Получение книги: " + bookId);
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/book/{bookId}/update")
    public String updateBook(@PathVariable String bookId,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add", false);
            model.addAttribute("book", book);
            return "edit";
        }
        log.info("Обновление книги с ид: " + bookId);
        book.setId(bookId);
        Book bookResult = bookService.save(book);
        model.addAttribute("book", bookResult);
        model.addAttribute("message", "Успешное создание книги");
        return "book";
    }

    @GetMapping("/book/{bookId}/delete")
    public String deleteBook(@PathVariable("bookId") String bookId) {
        log.info("Удаление книги с ид: " + bookId);
        bookService.deleteById(bookId);
        return "redirect:/book";
    }
}