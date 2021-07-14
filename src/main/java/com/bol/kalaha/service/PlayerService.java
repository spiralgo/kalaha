package com.bol.kalaha.service;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepoository) {
        this.playerRepository = playerRepoository;
    }

    public Player createPlayer(Player player) {
        player = playerRepository.save(player);
        return player;

    }
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

}
