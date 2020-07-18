package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.domain.CommentMongo;
import ru.otus.valeev.repository.CommentMongoRepository;
import ru.otus.valeev.service.BookService;
import ru.otus.valeev.service.CommentService;
import ru.otus.valeev.service.ConsoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMongoRepository commentMongoRepository;
    private final BookService bookService;
    private final ConsoleService consoleService;

    @Override
    @Transactional
    public boolean deleteById(Long commentId) {
        if (commentMongoRepository.existsById(commentId)) {
            commentMongoRepository.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public List<CommentMongo> deleteCommentsByBookId(Long id) {
        return commentMongoRepository.deleteCommentByBook_Id(id);
    }

    @Override
    @Transactional
    public CommentMongo save(Long bookId, String text) {
        BookMongo bookMongo = bookService.findById(bookId);
        if (bookMongo == null) {
            consoleService.sendMessage(String.format("Книга с ид  %s не найдена", bookId));
            return null;
        }
        return commentMongoRepository.save(new CommentMongo(text, bookMongo));
    }
}
