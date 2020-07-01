package ru.otus.valeev.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.valeev.domain.GenreMongo;
import ru.otus.valeev.service.SequenceGeneratorService;

@Component
@RequiredArgsConstructor
public class GenreModelListener extends AbstractMongoEventListener<GenreMongo> {
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<GenreMongo> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(GenreMongo.COLLECTION_NAME));
        }
    }
}
