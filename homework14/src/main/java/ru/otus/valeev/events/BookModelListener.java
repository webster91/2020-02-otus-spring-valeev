package ru.otus.valeev.events;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.BookMongo;
import ru.otus.valeev.service.SequenceGeneratorService;

@Component
@AllArgsConstructor
public class BookModelListener extends AbstractMongoEventListener<BookMongo> {
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BookMongo> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(BookMongo.COLLECTION_NAME));
        }
    }
}
