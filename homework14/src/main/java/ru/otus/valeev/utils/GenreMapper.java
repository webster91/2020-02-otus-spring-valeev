package ru.otus.valeev.utils;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.otus.valeev.domain.GenreJpa;
import ru.otus.valeev.domain.GenreMongo;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface GenreMapper {

    GenreJpa toGenreJpa(GenreMongo authorMongo);

    GenreMongo toGenreMongo(GenreJpa authorJpa);
}
