package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.CommentService;
import ru.otus.valeev.service.ConsoleService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookService bookService;
    private final ConsoleService consoleService;

    @Override
    @Transactional
    public boolean deleteById(long commentId) {
        if (commentDao.existsById(commentId)) {
            commentDao.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Comment save(Long bookId, String comment) {
        Book book = bookService.findById(bookId);
        if (book == null) {
            consoleService.sendMessage(String.format("Книга с ид  %s не найдена", bookId));
            return null;
        }
        return commentDao.save(Comment.builder()
                .comment(comment)
                .book(book)
                .build());
    }
}
