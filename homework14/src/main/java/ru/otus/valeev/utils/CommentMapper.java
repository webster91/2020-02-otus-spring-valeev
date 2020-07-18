package ru.otus.valeev.utils;

import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.otus.valeev.domain.CommentJpa;
import ru.otus.valeev.domain.CommentMongo;

@Mapper(componentModel = "spring", uses = {BookMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
public interface CommentMapper {

    CommentJpa toCommentJpa(CommentMongo commentMongo, @Context CycleAvoidingMappingContext context);

    CommentMongo toComment(CommentJpa commentJpa, @Context CycleAvoidingMappingContext context);
}
