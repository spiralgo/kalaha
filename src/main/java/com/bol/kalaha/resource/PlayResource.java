package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PlayService;
import com.bol.kalaha.service.PlayerService;
import com.bol.kalaha.util.GameRules;
import com.bol.kalaha.util.MoveValidationUtil;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/play")
public class PlayResource {
    private GameService gameService;
    private PlayerService playerService;
    private BoardService boardService;
    private PlayService playService;
    private WebSocketResource webSocketResource;

    @Autowired
    public PlayResource(GameService gameService, PlayerService playerService,
                        BoardService boardService, PlayService playService,
                        WebSocketResource webSocketResource) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.boardService = boardService;
        this.playService = playService;
        this.webSocketResource = webSocketResource;
    }

    @RequestMapping(value = "/{gameId}/{playerId}/{position}", method = RequestMethod.POST)
    public ResponseEntity<Board> movePlay(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable Integer position) {
        Optional<Game> game = gameService.findById(gameId);
        Optional<Player> player = playerService.findById(playerId);
        Optional<Board> board = boardService.getBoardByGame(game.get());
        Board resultBoard;
        if (player.isPresent() && game.isPresent() && board.isPresent()) {

                ResponseEntity<Board> response = validateMove(game.get(), player.get(), position);
                if (response == null) {
                    boolean isPlayerOne = (player.get().equals(game.get().getPlayerOne()));
                    resultBoard = board.get();
                    int theLastPosition = playService.movePlay(resultBoard, isPlayerOne, position);
                    if (resultBoard == null) return ResponseEntity.badRequest().build();
                    GameRules gameRules = new GameRules();
                    if (gameRules.checkGameOver(board.get())) {
                        gameService.finishGame(game.get());
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.END,
                                "The game ends.", game.get()), game.get().getId());
                    } else {

                        if (!gameRules.checkExtraMove(isPlayerOne, theLastPosition)) {
                            gameRules.changeTurn(game.get());
                        }
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME,
                                "It is the turn of " + game.get().getTurnOfWithId().getName(), game.get()), game.get().getId());

                    }
                    boardService.updateBoard(board.get());
                    return ResponseEntity.ok(resultBoard);
                }
            }

        return ResponseEntity.badRequest().build();
    }

    //TODO: messages should be taken from an i18n properties file instead of hard-coding. Reduce if-else statements if possible.
    public ResponseEntity<Board> validateMove(Game game, Player player, Integer position) {
        String message = "";

        if (game.isOver()){
             message = "The game is over.";
        }else if (game.getPlayerTwo() == null){
             message = "You need an opponent.";
        }else if (!MoveValidationUtil.isMyTurn(game, player)){
            message = "It is not your turn.";
        }else if (game.getTurnOfWithId().equals(game.getPlayerOne())
                && (position < PlayService.PIT_0_PLAYER_ONE || position >= PlayService.KALAHA_PLAYER_ONE)){
            message = "Your pits are on the top row.";
        } else if (game.getTurnOfWithId().equals(game.getPlayerTwo())
                && (position < PlayService.PIT_0_PLAYER_TWO || position >= PlayService.KALAHA_PLAYER_TWO)){
            message = "Your pits are on the bottom row.";
        } else if (game.getBoard().getPits().get(position-1).getValue()==0){
            message=  "This pit is empty.";
        }

         if(message.length()>0)
         throw new ResourceException(HttpStatus.BAD_REQUEST,  message);


        return null;
    }
}

