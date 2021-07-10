package com.bol.kalaha.service;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.util.JoinAGameValidationEnum;
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
    public JoinAGameValidationEnum validateJoin(Game game, Player player) throws ResourceException {

        if (player.getId() == null){
            return JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER;
        }

        JoinAGameValidationEnum result;
        Player playerOne = game.getPlayerOne();
        Player playerTwo = game.getPlayerTwo();

        if (player.equals(playerOne) || player.equals(playerTwo)){
            result = JoinAGameValidationEnum.ALREADY_A_PLAYER;
        }else if(playerTwo == null){
            result = JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO;
        }else {
            result = JoinAGameValidationEnum.JOIN_AS_A_WIEVER;

        }
        return result;
    }

}
