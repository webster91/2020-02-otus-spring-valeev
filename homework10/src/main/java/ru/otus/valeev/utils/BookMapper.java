package ru.otus.valeev.utils;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.dto.BookDto;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto toBookDto(Book car);

    Book toBook(BookDto car);
}
