package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.valeev.dao.AuthorDao;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public List<Author> allAuthors() {
        return authorDao.findAll();
    }

    @Override
    @Transactional
    public Author saveAuthorByName(String name) {
        Author author = authorDao.findByName(name);
        if (author == null) {
            author = authorDao.save(Author.builder()
                    .name(name)
                    .build());
        }
        return author;
    }
}
