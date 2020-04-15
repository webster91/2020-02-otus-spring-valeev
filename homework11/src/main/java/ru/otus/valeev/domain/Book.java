package ru.otus.valeev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Books")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "vitaliy", attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("genre")
        }),
        @NamedEntityGraph(name = "allJoins", attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("genre"),
                @NamedAttributeNode("comments")
        })
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Comment> comments;
}