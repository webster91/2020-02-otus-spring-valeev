package ru.otus.valeev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Author getAuthorByName(String name) {
        return authorDao.getAuthorByName(name);
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorDao.getAuthorById(id);
    }

    @Override
    public Author saveAuthorByName(String name) {
        Author author = authorDao.getAuthorByName(name);
        if (author == null) {
            Author.builder()
                    .name(name)
                    .build();
            author = authorDao.save(Author.builder()
                    .name(name)
                    .build());
        }
        return author;
    }
}
