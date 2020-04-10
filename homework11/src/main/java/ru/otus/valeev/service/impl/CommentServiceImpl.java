package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.service.CommentService;
import ru.otus.valeev.service.ConsoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;
    private final ConsoleService consoleService;

    @Override
    public Comment getById(long id) {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> getByBookName(String bookName) {
        Book book = bookDao.getBookByName(bookName);
        if (book == null) {
            return null;
        }
        return commentDao.findAllByBookId(book.getId());
    }

    @Override
    public boolean deleteCommentById(long commentId) {
        return commentDao.deleteById(commentId);
    }

    @Override
    public Comment addComment(String bookName, String comment) {
        Book book = bookDao.getBookByName(bookName);
        if (book == null) {
            consoleService.sendMessage(String.format("Книги с названием  %s не найдена", bookName));
            return null;
        }
        return commentDao.save(Comment.builder()
                .comment(comment)
                .bookId(book.getId())
                .build());
    }
}
