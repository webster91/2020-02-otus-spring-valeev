package ru.otus.valeev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.PlayerDao;
import ru.otus.valeev.domain.Player;
import ru.otus.valeev.service.PlayerService;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;

    @Override
    public Player registerPlayer(String playerName) {
        return playerDao.createPlayer(playerName);
    }

    @Override
    public String getPlayerName() {
        return playerDao.getPlayerName();
    }
}
