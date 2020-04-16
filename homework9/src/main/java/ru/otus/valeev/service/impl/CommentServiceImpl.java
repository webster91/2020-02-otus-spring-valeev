package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Comment findById(long id) {
        return commentDao.findById(id);
    }

    @Override
    @Transactional
    public Comment deleteById(long commentId) {
        return commentDao.deleteById(commentId);
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
                .book(book)
                .build());
    }

    @Override
    @Transactional
    public List<Comment> findCommentsByBookName(String name) {
        Book byName = bookDao.findByName(name);
        if (byName == null) {
            return null;
        } else {
            Hibernate.initialize(byName.getComments());
            return byName.getComments();
        }
    }
}
