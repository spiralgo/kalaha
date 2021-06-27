package com.bol.kalaha.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.PlayersService;

@RestController
@RequestMapping ("/players")

public class PlayersResource {
	
	@Autowired
	private PlayersService playersService;
	
	
	
	
	public PlayersResource(PlayersService playersService) {
		super();
		this.playersService = playersService;
	}
	@GetMapping 
 	public List <Player> listAllPlayers () {
		return playersService.findAll();
	}

	
}
