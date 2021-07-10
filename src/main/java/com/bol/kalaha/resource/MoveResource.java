package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.MoveService;
import com.bol.kalaha.service.PlayerService;
import com.bol.kalaha.util.GameRulesService;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/move")
public class MoveResource {
    private GameService gameService;
    private PlayerService playerService;
    private BoardService boardService;
    private MoveService moveService;
    private WebSocketResource webSocketResource;
    private GameRulesService gameRulesService;

    @Autowired
    public MoveResource(GameService gameService, PlayerService playerService,
                        BoardService boardService, MoveService moveService,
                        WebSocketResource webSocketResource,
                        GameRulesService gameRulesService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.boardService = boardService;
        this.moveService = moveService;
        this.webSocketResource = webSocketResource;
        this.gameRulesService = gameRulesService;
    }

    @RequestMapping(value = "/{gameId}/{playerId}/{position}", method = RequestMethod.POST)
    public ResponseEntity<Board> movePlay(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable Integer position) {
        Optional<Game> game = gameService.findById(gameId);
        Optional<Player> player = playerService.findById(playerId);

        if (player.isPresent() && game.isPresent()) {

               boolean isValidMove = moveService.validateMove(game.get(), player.get(), position);
                if (isValidMove) {
                    boolean isPlayerOne = (player.get().equals(game.get().getPlayerOne()));
                    Board resultBoard = game.get().getBoard();
                    int theLastPosition = moveService.movePlay(resultBoard, isPlayerOne, position);

                    if (resultBoard == null) return ResponseEntity.badRequest().build();

                    if (gameRulesService.checkGameOver(resultBoard)) {
                        gameService.finishGame(game.get());
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.END,
                                "The game ends.", game.get()), game.get().getId());
                    } else {

                        if (!gameRulesService.checkExtraMove(isPlayerOne, theLastPosition)) {
                            gameRulesService.changeTurn(game.get());
                        }
                        webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME,
                                "It is the turn of " + game.get().getTurnOf().getName(), game.get()), game.get().getId());

                    }
                    boardService.updateBoard(resultBoard);

                    return ResponseEntity.ok(resultBoard);
                }
            }

        return ResponseEntity.badRequest().build();
    }


}

