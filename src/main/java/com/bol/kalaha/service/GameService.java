package com.bol.kalaha.service;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.util.GameValidationUtil;
import com.bol.kalaha.util.JoinAGameValidationEnum;
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
    public Optional<ResponseData<Game>> startJoinProcess(Long gameId, Player player)
            throws KalahaException {

        Optional<Game> gameOptional = findById(gameId);
        String message = "";

        ResponseData<Game> responseData = null;

        if(gameOptional.isPresent()) {

          Game game = gameOptional.get();
          JoinAGameValidationEnum answer = GameValidationUtil.validateJoin(game, player);
          message = generateMessageFromAnswer(answer, player);
          if (answer == JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO) {
                game.setPlayerTwo(player);
                joinGame(game);
          }

            responseData = new ResponseData<>();
            responseData.setBody(game);
            responseData.setMessage(message);

        }else {
            throw new KalahaException(HttpStatus.BAD_REQUEST, "Game not found.");
        }

        return Optional.of(responseData);
    }
    String generateMessageFromAnswer(JoinAGameValidationEnum answer, Player player) throws KalahaException {
        StringBuilder message = new StringBuilder();
        if (answer == JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER) {
            message.append(SHOULD_CREATE_PLAYER.getValue());
            throw new KalahaException(HttpStatus.BAD_REQUEST, message.toString());
        } else if (answer == JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO) {
            message.append(player.getName()).append(JOINS_OPPONENT.getValue());
        } else if (answer == JoinAGameValidationEnum.ALREADY_A_PLAYER) {
            message.append(player.getName()).append(REJOINS.getValue());
        } else if (answer == JoinAGameValidationEnum.JOIN_AS_A_WIEVER) {
            message.append(player.getName()).append(JOINS_VIEWER.getValue());
        }
        return message.toString();
    }
}
