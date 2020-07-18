package ru.otus.valeev.utils;

import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.otus.valeev.domain.BookJpa;
import ru.otus.valeev.domain.BookMongo;

@Mapper(componentModel = "spring", uses = {CommentMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
public interface BookMapper {

    BookJpa toBookJpa(BookMongo book, @Context CycleAvoidingMappingContext context);

    BookMongo toBook(BookJpa bookJpa, @Context CycleAvoidingMappingContext context);
}
