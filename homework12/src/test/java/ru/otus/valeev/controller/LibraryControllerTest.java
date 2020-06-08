package ru.otus.valeev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.valeev.Main;
import ru.otus.valeev.dao.BookDao;
import ru.otus.valeev.domain.Book;
import ru.otus.valeev.dto.BookDto;
import ru.otus.valeev.utils.BookMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@WithMockUser(
        username = "admin",
        authorities = {"ADMIN"}
)
@AutoConfigureMockMvc
class LibraryControllerTest {
    private BookDto bookDto;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookDao bookDao;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init() {
        Book book = bookDao.findAll().stream()
                .findFirst()
                .orElseThrow();
        bookDto = BookMapper.INSTANCE.toBookDto(book);
    }

    @Test
    @DisplayName("Получение всех книг")
    void shouldGetAllBooks() throws Exception {
        mvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Сохранение книги")
    void createBook() throws Exception {
        bookDto.setId(null);
        mvc.perform(post("/api/book")
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Получение книги")
    void getBook() throws Exception {
        Book book = bookDao.findAll().stream()
                .findFirst()
                .orElseThrow();

        mvc.perform(get("/api/book/{bookId}", book.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()));
    }

    @Test
    @DisplayName("Обновление книги")
    void updateBook() throws Exception {
        String bookName = "Best of the best";
        bookDto.setName(bookName);

        mvc.perform(patch("/api/book/")
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }

    @Test
    @DisplayName("Удаление книги")
    void deleteBook() throws Exception {
        String id = bookDto.getId();
        mvc.perform(delete("/api/book/{bookId}", id)
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        boolean isExists = bookDao.existsById(id);
        assertThat(isExists).isEqualTo(false);
    }
}