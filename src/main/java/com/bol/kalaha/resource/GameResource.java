package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.util.BoardUtil;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.SHOULD_CREATE_PLAYER;


@RestController
@RequestMapping("/game")
public class GameResource {

    private final GameService gameService;

    private final WebSocketResource webSocketResource;

    @Autowired
    public GameResource(GameService gameService, WebSocketResource webSocketResource) {
        this.gameService = gameService;
        this.webSocketResource = webSocketResource;
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> createNewGame(@RequestBody Player playerOne) throws KalahaException {
        if (playerOne.getId() == null)
            throw new KalahaException(HttpStatus.BAD_REQUEST, SHOULD_CREATE_PLAYER.getValue());

        Game createdGame = new Game();
        createdGame.setPlayerOne(playerOne);
        Board board = BoardUtil.initiateABoard();
        board.setGame(createdGame);
        createdGame.setBoard(board);
        createdGame.setTurnOf(playerOne);
        gameService.createNewGame(createdGame);
        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME_LIST,
                "Game #" + createdGame.getId() + " is created by " + playerOne.getName(), null));

        return ResponseEntity.status(HttpStatus.OK).body(createdGame);
    }

    @PatchMapping(value = "/join/{gameId}")
    @ResponseBody
    public ResponseEntity<Game> joinGame(@PathVariable Long gameId, @RequestBody Player player) throws KalahaException {

        Optional<ResponseData<Game>> responseDataOptional = gameService.startJoinProcess(gameId, player);
        Game game = new Game();
        if (responseDataOptional.isPresent()) {

            ResponseData<Game> responseData = responseDataOptional.get();
            game = responseData.getBody();
            webSocketResource.publishWebSocket(
                    WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME
                            , responseData.getMessage(), game)
                    , game.getId());

        }

        return ResponseEntity.ok(game);

    }

    @GetMapping
    public List<Game> getGamesToJoin() {
        return gameService.getGames();
    }

}
