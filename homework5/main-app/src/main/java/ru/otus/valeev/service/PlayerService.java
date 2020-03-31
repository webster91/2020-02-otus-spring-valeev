package ru.otus.valeev.service;

import ru.otus.valeev.domain.Player;

public interface PlayerService {
    Player registerPlayer(String playerName);

    String getPlayerName();
}
