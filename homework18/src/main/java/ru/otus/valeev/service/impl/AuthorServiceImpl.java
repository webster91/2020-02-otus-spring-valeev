package ru.otus.valeev.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.service.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final Random random = new Random();

    @Override
    @HystrixCommand(commandKey = "findAll", fallbackMethod = "fallbackGetAuthors")
    public List<Author> allAuthors() {
        boolean rnd = random.nextBoolean();
        if (rnd) {
            throw new RuntimeException("Не повезло");
        }
        return authorDao.findAll();
    }

    @Override
    @Transactional
    public Author saveAuthorByName(String name) {
        Author author = authorDao.findByName(name);
        if (author == null) {
            author = authorDao.save(new Author(name));
        }
        return author;
    }

    private List<Author> fallbackGetAuthors() {
        Author author = new Author();
        author.setId("0");
        author.setName("Сын маминой подруги");
        return Collections.singletonList(author);
    }
}
