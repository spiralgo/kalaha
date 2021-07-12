package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.util.BoardUtil;
import com.bol.kalaha.util.GameValidationUtil;
import com.bol.kalaha.util.JoinAGameValidationEnum;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.*;


@RestController
@RequestMapping("/game")
public class GameResource {

    private GameService gameService;

    private WebSocketResource webSocketResource;

    @Autowired
    public GameResource(GameService gameService, WebSocketResource webSocketResource) {
        this.gameService = gameService;
        this.webSocketResource = webSocketResource;
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> createNewGame(@RequestBody Player playerOne, HttpServletResponse response) {
         if (playerOne.getId() == null)
            throw new ResourceException(HttpStatus.BAD_REQUEST, SHOULD_CREATE_PLAYER.getValue());

        Game createdGame = new Game();
        createdGame.setPlayerOne(playerOne);
        Board board = BoardUtil.initiateABoard();
        board.setGame(createdGame);
        createdGame.setBoard(board);
        createdGame.setTurnOf(playerOne);
        gameService.createNewGame(createdGame);
        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME_LIST,
                "Game #" + createdGame.getId() + " is created by " + playerOne.getName(), null));

        return ResponseEntity.ok(createdGame);
    }

    @PatchMapping(value = "/join/{gameId}")
    @ResponseBody
    public ResponseEntity<Game> joinGame(@PathVariable Long gameId, @RequestBody Player player) {
        Optional<Game> gameOptional = gameService.findById(gameId);
        Game game = gameOptional.get();
        if(gameOptional.isPresent()) {
            JoinAGameValidationEnum answer = GameValidationUtil.validateJoin(game, player);
            String message = gameService.startJoinProcess(answer, game, player);
            webSocketResource.publishWebSocket(
                    WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME, message, game), game.getId());

        }else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Game not found.");
        }
            return ResponseEntity.ok(game);
    }

    @GetMapping
    @ResponseBody
    public List<Game> getGamesToJoin() {
        List<Game> games = gameService.getGames();
        return games;
    }

}
