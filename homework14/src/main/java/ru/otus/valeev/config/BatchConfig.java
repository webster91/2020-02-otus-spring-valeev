package ru.otus.valeev.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.otus.valeev.domain.*;
import ru.otus.valeev.repository.AuthorMongoRepository;
import ru.otus.valeev.repository.BookMongoRepository;
import ru.otus.valeev.repository.CommentMongoRepository;
import ru.otus.valeev.repository.GenreMongoRepository;
import ru.otus.valeev.utils.*;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;

@EnableBatchProcessing
@Configuration
@AllArgsConstructor
@Slf4j
public class BatchConfig {

    private static final int CHUNK_SIZE = 5;
    private static final int PAGE_SIZE = 20;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AuthorMongoRepository authorMongoRepository;
    private final BookMongoRepository bookMongoRepository;
    private final CommentMongoRepository commentMongoRepository;
    private final GenreMongoRepository genreMongoRepository;

    private final EntityManagerFactory emf;

    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;
    private final CommentMapper commentMapper;

    @Bean
    public ItemReader<AuthorMongo> authorReader() {
        return new RepositoryItemReaderBuilder<AuthorMongo>()
                .name("authorReader")
                .pageSize(PAGE_SIZE)
                .sorts(new HashMap<>())
                .repository(authorMongoRepository)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemReader<BookMongo> bookReader() {
        return new RepositoryItemReaderBuilder<BookMongo>()
                .name("bookReader")
                .pageSize(PAGE_SIZE)
                .sorts(new HashMap<>())
                .repository(bookMongoRepository)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemReader<CommentMongo> commentReader() {
        return new RepositoryItemReaderBuilder<CommentMongo>()
                .name("commentReader")
                .pageSize(PAGE_SIZE)
                .sorts(new HashMap<>())
                .repository(commentMongoRepository)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemReader<GenreMongo> genreReader() {
        return new RepositoryItemReaderBuilder<GenreMongo>()
                .name("genreReader")
                .pageSize(PAGE_SIZE)
                .sorts(new HashMap<>())
                .repository(genreMongoRepository)
                .methodName("findAll")
                .build();
    }

    @Bean
    ItemProcessor<AuthorMongo, AuthorJpa> authorProcessor() {
        return authorMapper::toAuthorJpa;
    }

    @Bean
    ItemProcessor<BookMongo, BookJpa> bookProcessor() {
        return bookMongo -> bookMapper.toBookJpa(bookMongo, new CycleAvoidingMappingContext());
    }

    @Bean
    ItemProcessor<CommentMongo, CommentJpa> commentProcessor() {
        return commentMongo -> commentMapper.toCommentJpa(commentMongo, new CycleAvoidingMappingContext());
    }

    @Bean
    ItemProcessor<GenreMongo, GenreJpa> genreProcessor() {
        return genreMapper::toGenreJpa;
    }


    @Bean
    public ItemWriter<AuthorJpa> authorWriter() {
        JpaItemWriter<AuthorJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public ItemWriter<BookJpa> bookWriter() {
        JpaItemWriter<BookJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public ItemWriter<CommentJpa> commentWriter() {
        JpaItemWriter<CommentJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public ItemWriter<GenreJpa> genreWriter() {
        JpaItemWriter<GenreJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Step authorStep(ItemReader<AuthorMongo> reader, ItemProcessor<AuthorMongo, AuthorJpa> itemProcessor, ItemWriter<AuthorJpa> writer) {
        return stepGenerator("Миграция авторов", reader, itemProcessor, writer);
    }

    @Bean
    public Step bookStep(ItemReader<BookMongo> reader, ItemProcessor<BookMongo, BookJpa> itemProcessor, ItemWriter<BookJpa> writer) {
        return stepGenerator("Миграция книг", reader, itemProcessor, writer);
    }

    @Bean
    public Step commentStep(ItemReader<CommentMongo> reader, ItemProcessor<CommentMongo, CommentJpa> itemProcessor, ItemWriter<CommentJpa> writer) {
        return stepGenerator("Миграция комментарий", reader, itemProcessor, writer);
    }

    @Bean
    public Step genresStep(ItemReader<GenreMongo> reader, ItemProcessor<GenreMongo, GenreJpa> itemProcessor, ItemWriter<GenreJpa> writer) {
        return stepGenerator("Миграция жанров", reader, itemProcessor, writer);
    }

    private <I, O> Step stepGenerator(String stepName, ItemReader<I> reader, ItemProcessor<I, O> itemProcessor, ItemWriter<? super O> writer) {
        return stepBuilderFactory.get(stepName)
                .<I, O>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(stepExecutionListener())
                .listener(processListener())
                .listener(writeListener())
                .build();
    }

    @Bean
    public Job migrateDataJob() {
        return jobBuilderFactory.get("importDataJob")
                .start(authorStep(authorReader(), authorProcessor(), authorWriter()))
                .next(genresStep(genreReader(), genreProcessor(), genreWriter()))
                .next(bookStep(bookReader(), bookProcessor(), bookWriter()))
                .next(commentStep(commentReader(), commentProcessor(), commentWriter()))
                .listener(jobExecutionListener())
                .build();
    }

    private <T, S> ItemProcessListener<T, S> processListener() {
        return new ItemProcessListener<>() {
            public void beforeProcess(@NonNull Object o) {
                log.info("Начало обработки {}", o);
            }

            public void afterProcess(@NonNull Object o, Object o2) {
                log.info("Конец обработки {}", o2);
            }

            public void onProcessError(@NonNull Object o, @NonNull Exception e) {
                log.info("Ошбка обработки {}. Ошибка: {}", o, e);
            }
        };
    }

    private <S> ItemWriteListener<S> writeListener() {
        return new ItemWriteListener<>() {
            public void beforeWrite(@NonNull List list) {
                log.info("Начало записи {}", list);
            }

            public void afterWrite(@NonNull List list) {
                log.info("Конец записи: Успешно");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List list) {
                log.error("Конец записи: Ошибка", e);
            }
        };
    }

    private StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(@NonNull StepExecution stepExecution) {
                log.info("----------------------------------------------");
                log.info("Начало выполнения шага: {}", stepExecution.getStepName());
            }

            @Override
            public ExitStatus afterStep(@NonNull StepExecution stepExecution) {
                log.info("Конец шага");
                log.info("----------------------------------------------");
                return null;
            }
        };
    }

    private JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(@NonNull JobExecution jobExecution) {
                log.info("Запущен процесс миграции");
            }

            @Override
            public void afterJob(@NonNull JobExecution jobExecution) {
                log.info("Процесс миграции окончен");
            }
        };
    }

}
