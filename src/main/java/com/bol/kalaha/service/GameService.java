package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame(Game game) {
        game = gameRepository.save(game);
        return game;
    }

    public Optional<Game> findById(Long gameId) {
           return gameRepository.findById(gameId);
    }
    public Game joinGame(Game game) {
        Game result = gameRepository.save(game);
        return result;
    }
    public Game finishGame(Game game) {
        game.setOver(true);
        Game result = gameRepository.save(game);
        return result;

    }
    public List<Game> getGames() {
        List<Game> games = gameRepository.findAllByOrderByIdDesc();
        return games;
    }
}
