package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.util.BoardUtil;
import com.bol.kalaha.util.JoinAGameValidationEnum;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


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
            throw new ResourceException(HttpStatus.BAD_REQUEST,  "You need to create a user first.");

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
        if(gameOptional.isPresent()){
            String message = "You need to create a user first.";

            JoinAGameValidationEnum answer = validateJoin(game, player);

            if (answer == JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, message);
            }else if (answer == JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO) {
                game.setPlayerTwo(player);
                gameService.joinGame(game);
                message = player.getName() +" joins the game as the opponent.";
            }else if (answer == JoinAGameValidationEnum.ALREADY_A_PLAYER) {
                message = player.getName() +" rejoins the game.";
            }else{
                message = player.getName() +" joins the game as a viewer.";
             }
            webSocketResource.publishWebSocket(
                    WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME,message, game), game.getId());

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


    private JoinAGameValidationEnum validateJoin(Game game, Player player) throws ResourceException {
        JoinAGameValidationEnum result = JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER;

        if (player.getId() == null)
            return result;

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
