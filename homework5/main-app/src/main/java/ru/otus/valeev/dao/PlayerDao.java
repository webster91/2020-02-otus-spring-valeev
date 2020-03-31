package ru.otus.valeev.dao;

import ru.otus.valeev.domain.Player;

public interface PlayerDao {
    Player createPlayer(String playerName);

    String getPlayerName();
}