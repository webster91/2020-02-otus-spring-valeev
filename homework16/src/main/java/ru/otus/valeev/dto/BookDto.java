package ru.otus.valeev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.valeev.domain.Author;
import ru.otus.valeev.domain.Comment;
import ru.otus.valeev.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private Author author;
    private Genre genre;
    private List<Comment> comments = new ArrayList<>();
}
