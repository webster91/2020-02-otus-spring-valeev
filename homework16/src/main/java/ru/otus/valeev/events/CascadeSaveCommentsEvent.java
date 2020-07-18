package ru.otus.valeev.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.service.BookService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CascadeSaveCommentsEvent extends AbstractMongoEventListener<Comment> {
    private final BookService bookService;

    @Override
    public void onAfterSave(AfterSaveEvent<Comment> event) {
        Comment comment = event.getSource();
        Optional.of(comment)
                .map(Comment::getBook)
                .ifPresent(book -> {
                    book.getComments().add(comment);
                    bookService.save(book);
                });
    }
}
