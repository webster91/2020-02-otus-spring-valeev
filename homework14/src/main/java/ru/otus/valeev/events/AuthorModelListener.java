package ru.otus.valeev.events;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.AuthorMongo;
import ru.otus.valeev.service.SequenceGeneratorService;

@Component
@AllArgsConstructor
public class AuthorModelListener extends AbstractMongoEventListener<AuthorMongo> {
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<AuthorMongo> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(AuthorMongo.COLLECTION_NAME));
        }
    }
}
