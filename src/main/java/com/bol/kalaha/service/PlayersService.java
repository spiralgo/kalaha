package com.bol.kalaha.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;

@Service
@Transactional
public class PlayersService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	
	
	public List<Player> findAll () {
		return playerRepository.findAll();
	}

}
