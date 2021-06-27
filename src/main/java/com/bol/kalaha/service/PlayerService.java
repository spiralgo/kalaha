package com.bol.kalaha.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
@Service
@Transactional
public class PlayerService {
	
	
	@Autowired
	private PlayerRepository playerRepository;

	public PlayerService() {
		// TODO Auto-generated constructor stub
	}
	
	public PlayerService(PlayerRepository playerRepoository) {
		this.playerRepository = playerRepoository;
	}

	public Player createPlayer (Player player) {		
		playerRepository.save(player);
		return player;
		
	}
	
	public Optional<Player> findById (Long id) {
		return playerRepository.findById(id);
	}

}
