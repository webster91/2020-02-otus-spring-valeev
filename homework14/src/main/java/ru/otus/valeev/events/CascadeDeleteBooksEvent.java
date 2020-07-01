package ru.otus.valeev.events;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.service.CommentService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CascadeDeleteBooksEvent extends AbstractMongoEventListener<BookMongo> {
    private final CommentService commentService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<BookMongo> event) {
        Optional.ofNullable(event.getDocument())
                .map(doc -> doc.getObjectId("_id"))
                .ifPresent(id -> commentService.deleteCommentsByBookId(Long.valueOf(id.toString())));
    }
}
