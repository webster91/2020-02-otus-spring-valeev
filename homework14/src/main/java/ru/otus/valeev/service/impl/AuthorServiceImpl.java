package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.domain.AuthorMongo;
import ru.otus.valeev.repository.AuthorMongoRepository;
import ru.otus.valeev.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorMongoRepository authorMongoRepository;

    @Override
    public List<AuthorMongo> allAuthors() {
        return authorMongoRepository.findAll();
    }

    @Override
    @Transactional
    public AuthorMongo saveAuthorByName(String name) {
        AuthorMongo authorMongo = authorMongoRepository.findByName(name);
        if (authorMongo == null) {
            authorMongo = authorMongoRepository.save(new AuthorMongo(name));
        }
        return authorMongo;
    }
}
