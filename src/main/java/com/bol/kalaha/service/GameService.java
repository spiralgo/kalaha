package com.bol.kalaha.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
@Service
@Transactional
public class GameService {

	
	@Autowired
	private GameRepository gameRepository;
	
	public GameService() {

	}
	
	
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}



	public Game createNewGame (Player playerOne, Player playerTwo) {		
		Game game = new Game (playerOne, playerTwo);
		gameRepository.save(game);
		return game;
	}

	public Optional<Game> findById(Long gameId) {
		return gameRepository.findById(gameId);
	}
	
	public Game changeTurn (Game game) {
		if (game.getTurnOfWithId().equals(game.getPlayerOne())) 
			game.setTurnOfWithId(game.getPlayerTwo());
		else
			game.setTurnOfWithId(game.getPlayerOne());		
		
		
		return game;
	}

	public Game joinGame(Game game) {
		Game result = saveGame(game);
		return result;
	}

	public Game finishGame(Game game) {
		game.setOver(true);
		Game result = gameRepository.save(game);
		return result;
			
	}
	
	public List<Game> getGamesToJoin() {
	    List<Game> games = gameRepository.findAll();
	    return games;
	}

	public List<Game> getPlayerGames(Player player) {
		List <Game> gamesAsPlayerOne = gameRepository.findByPlayerOne (player);
		List <Game> gamesAsPlayerTwo = gameRepository.findByPlayerTwo (player);
		gamesAsPlayerOne.addAll(gamesAsPlayerTwo);
		return gamesAsPlayerOne;		
	}


	public Game saveGame(Game game) {
		Game result = gameRepository.save(game);
		return result;
		
	}

}
