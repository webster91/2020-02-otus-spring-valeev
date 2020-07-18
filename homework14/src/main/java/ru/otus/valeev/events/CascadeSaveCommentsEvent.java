package ru.otus.valeev.events;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.CommentMongo;
import ru.otus.valeev.service.BookService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CascadeSaveCommentsEvent extends AbstractMongoEventListener<CommentMongo> {
    private final BookService bookService;

    @Override
    public void onAfterSave(AfterSaveEvent<CommentMongo> event) {
        CommentMongo commentMongo = event.getSource();
        Optional.of(commentMongo)
                .map(CommentMongo::getBook)
                .ifPresentOrElse(book -> {
                    book.getComments().add(commentMongo);
                    bookService.save(book);
                }, () -> {
                    throw new RuntimeException();
                });
    }
}
