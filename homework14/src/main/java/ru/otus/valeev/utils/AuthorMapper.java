package ru.otus.valeev.utils;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.otus.valeev.domain.AuthorJpa;
import ru.otus.valeev.domain.AuthorMongo;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface AuthorMapper {

    AuthorJpa toAuthorJpa(AuthorMongo authorMongo);

    AuthorMongo toAuthorMongo(AuthorJpa authorJpa);
}
