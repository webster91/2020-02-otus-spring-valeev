package ru.otus.valeev.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.valeev.dao.PlayerDao;
import ru.otus.valeev.domain.Player;

@Service
public class PlayerDaoImpl implements PlayerDao {

    private Player player;

    @Override
    public String getPlayerName() {
        if (player == null) {
            return null;
        }
        return player.getName();
    }

    @Override
    public Player createPlayer(String playerName) {
        if (StringUtils.isBlank(playerName)) {
            return null;
        }
        player = new Player(playerName);
        return player;
    }
}
