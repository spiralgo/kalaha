package com.bol.kalaha.service;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.PLAYER_ALREADY_EXISTS;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepoository) {
        this.playerRepository = playerRepoository;
    }

    public Player createPlayer(Player player) throws KalahaException {
        if(findByName(player.getName().trim()).isPresent())
            throw new KalahaException(HttpStatus.BAD_REQUEST,
                    PLAYER_ALREADY_EXISTS.getValue());

        player = playerRepository.save(player);
        return player;

    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }
    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

}
