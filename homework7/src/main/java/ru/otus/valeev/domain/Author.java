package ru.otus.valeev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Author {
    private long id;
    private String name;
}