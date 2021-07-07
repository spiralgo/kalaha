package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PlayService;
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
    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PlayService playService;

    @Autowired
    private WebSocketResource webSocketResource;

    @RequestMapping(value = "/{gameId}/{playerId}/{position}", method = RequestMethod.POST)
    public ResponseEntity<Board> movePlay(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable Integer position) {
        Optional<Game> game = gameService.findById(gameId);
        Optional<Player> player = playerRepository.findById(playerId);
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
                        playService.finishGame(board.get().getGame());
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.END,
                                "The game ends."), board.get().getGame().getId());
                    } else {

                        if (!gameRules.checkExtraMove(isPlayerOne, theLastPosition)) {
                            gameService.changeTurn(game.get());
                        }
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_BOARD,
                                "It is the turn of " + game.get().getTurnOfWithId().getName()), game.get().getId());

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

