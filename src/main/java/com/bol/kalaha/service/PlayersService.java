package com.bol.kalaha.service;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlayersService {

    @Autowired
    private PlayerRepository playerRepository;


    public List<Player> findAll() {
        return playerRepository.findAll();
    }

}
