package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.dao.CommentDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.service.CommentService;
import ru.otus.valeev.service.ConsoleService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;
    private final ConsoleService consoleService;

    @Override
    public Comment findById(long id) {
        return commentDao.findById(id).orElse(null);
    }

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
    public Comment save(String bookName, String comment) {
        Book book = bookDao.findByName(bookName);
        if (book == null) {
            consoleService.sendMessage(String.format("Книга с названием  %s не найдена", bookName));
            return null;
        }
        return commentDao.save(Comment.builder()
                .comment(comment)
                .bookId(book.getId())
                .build());
    }
}
