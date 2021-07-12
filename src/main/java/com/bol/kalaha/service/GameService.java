package com.bol.kalaha.service;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.util.JoinAGameValidationEnum;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.*;
import static com.bol.kalaha.util.MessagesEnum.JOINS_VIEWER;

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
    public String startJoinProcess(JoinAGameValidationEnum answer, Game game, Player player) {
        StringBuilder message = new StringBuilder();

        if (answer == JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER) {
            message.append(SHOULD_CREATE_PLAYER.getValue());
            throw new ResourceException(HttpStatus.BAD_REQUEST, message.toString());
        } else if (answer == JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO) {
            game.setPlayerTwo(player);
            joinGame(game);
            message.append(player.getName()).append(JOINS_OPPONENT.getValue());
        } else if (answer == JoinAGameValidationEnum.ALREADY_A_PLAYER) {
            message.append(player.getName()).append(REJOINS.getValue());
        } else if (answer == JoinAGameValidationEnum.JOIN_AS_A_WIEVER) {
            message.append(player.getName()).append(JOINS_VIEWER.getValue());
        }
        return message.toString();
    }

}
