package com.bol.kalaha.service;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Board;
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

import static com.bol.kalaha.config.WebSocketActionEnum.END;
import static com.bol.kalaha.config.WebSocketActionEnum.REFRESH_GAME;
import static com.bol.kalaha.util.GameValidationUtil.validateJoin;
import static com.bol.kalaha.util.MessagesEnum.*;
import static com.bol.kalaha.util.MessagesEnum.JOINS_VIEWER;

@Service
public class GameService {
    private GameRepository gameRepository;
    private GameRulesService gameRulesService;
    private BoardService boardService;

    @Autowired
    public GameService(GameRepository gameRepository,
                       GameRulesService gameRulesService,
                       BoardService boardService) {
        this.gameRepository = gameRepository;
        this.gameRulesService = gameRulesService;
        this.boardService = boardService;
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
          JoinAGameValidationEnum answer = validateJoin(game, player);
          message = generateMessageFromAnswer(answer, player);
          if (answer == JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO) {
                game.setPlayerTwo(player);
                joinGame(game);
          }

            responseData = new ResponseData<>();
            responseData.setBody(game);
            responseData.setMessage(message);

        }else {
            throw new KalahaException(HttpStatus.BAD_REQUEST, GAME_NOT_FOUND.getValue());
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

    public String updateGameState(Board resultBoard, boolean isPlayerOne, int theLastPosition) {

            String message = "";
            Game game = resultBoard.getGame();
            if (gameRulesService.checkGameOver(resultBoard)) {
                finishGame(resultBoard.getGame());
                message = WebSocketUtil.getMessageJSON(END,
                        "The game ends.", game);
            } else {

                if (!gameRulesService.checkExtraMove(isPlayerOne, theLastPosition)) {
                    gameRulesService.changeTurn(game);
                }
                message = WebSocketUtil.getMessageJSON(REFRESH_GAME,
                        "It is the turn of "
                                + game.getTurnOf().getName()
                        , game);

            }

            boardService.updateBoard(resultBoard);
            return message;

    }
}
