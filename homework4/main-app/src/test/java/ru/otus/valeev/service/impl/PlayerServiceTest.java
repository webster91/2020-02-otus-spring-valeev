package ru.otus.valeev.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.valeev.dao.PlayerDao;
import ru.otus.valeev.domain.Player;
import ru.otus.valeev.service.PlayerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс PlayerService")
@SpringBootTest
class PlayerServiceTest {

    @TestConfiguration
    @ComponentScan("ru.otus.valeev.service")
    static class ServiceConfiguration {
    }

    @Autowired
    private PlayerService playerService;
    @MockBean
    private PlayerDao playerDao;

    @Test
    @DisplayName("Проверка корректной регистрации пользователя")
    void registerPlayer() {
        String name = "Ignat";
        Player player = new Player(name);
        given(playerDao.createPlayer(name))
                .willReturn(player);
        assertDoesNotThrow(() -> playerService.registerPlayer(name));
    }

    @Test
    @DisplayName("Проверка создания пользователя с именем пользователя - пустая строка")
    void registerEmptyPlayerName() {
        String name = "";
        given(playerDao.createPlayer(name))
                .willReturn(null);
        assertThat(playerService.registerPlayer(name)).isEqualTo(null);

    }

    @Test
    @DisplayName("Проверка создания пользователя без указания имени")
    void registerNullPlayerName() {
        given(playerDao.createPlayer(null))
                .willReturn(null);
        assertThat(playerService.registerPlayer(null)).isEqualTo(null);

    }

    @Test
    @DisplayName("Проверка корректного получения имени пользователя")
    void getPlayerName() {
        String name = "Ignat";
        given(playerDao.getPlayerName())
                .willReturn(name);
        assertEquals(playerService.getPlayerName(), name);
    }

    @Test
    @DisplayName("Проверка некорректного получения имени пользователя")
    void getEmptyPlayerName() {
        given(playerDao.getPlayerName())
                .willReturn(null);
        assertThat(playerService.getPlayerName()).isEqualTo(null);
    }
}