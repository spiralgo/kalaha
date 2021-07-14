package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.*;
import com.bol.kalaha.util.MoveValidationUtil;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.bol.kalaha.config.WebSocketActionEnum.END;
import static com.bol.kalaha.config.WebSocketActionEnum.REFRESH_GAME;

@RestController
@RequestMapping("/move")
public class MoveResource {
    private final GameService gameService;
    private final PlayerService playerService;
    private final MoveService moveService;
    private final WebSocketResource webSocketResource;

    @Autowired
    public MoveResource(GameService gameService,
                        PlayerService playerService,
                        MoveService moveService,
                        WebSocketResource webSocketResource) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.moveService = moveService;
        this.webSocketResource = webSocketResource;
     }

    @PostMapping(value = "/{gameId}/{playerId}/{position}")
    public ResponseEntity<Board> movePlay(@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable Integer position) throws KalahaException {
        Optional<Game> game = gameService.findById(gameId);
        Optional<Player> player = playerService.findById(playerId);

        if (player.isPresent() && game.isPresent()) {

            boolean isValidMove = MoveValidationUtil.validateMove(game.get(), player.get(), position);
            if (isValidMove) {
                boolean isPlayerOne = (player.get().equals(game.get().getPlayerOne()));
                Board resultBoard = game.get().getBoard();
                int theLastPosition = moveService.movePlay(resultBoard, isPlayerOne, position);
                String message = gameService.updateGameState(resultBoard, isPlayerOne, theLastPosition);
                webSocketResource.publishWebSocket(message, game.get().getId());
                return ResponseEntity.ok(resultBoard);
            }
        }

        return ResponseEntity.badRequest().build();
    }


}

