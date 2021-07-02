package com.bol.kalaha.resource;

import java.util.Optional;


import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.util.GameRules;
import com.bol.kalaha.util.MoveValidationUtil;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bol.kalaha.repository.PlayerRepository;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PlayService;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;

@RestController
@RequestMapping ("/play")
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
	
	
	
	@RequestMapping(value="/{gameId}/{playerId}/{position}", method = RequestMethod.POST)
	public ResponseEntity<Board> movePlay (@PathVariable Long gameId, @PathVariable Long playerId, @PathVariable Integer position) {
		Optional<Player> player = playerRepository.findById(playerId);
		Optional<Game> game = gameService.findById(gameId);
		Optional <Board> board = boardService.getBoardByGame(game.get());
		Board resultBoard;
		if (player.isPresent() && game.isPresent() && board.isPresent())  {
			if (!playService.checkGameOver(board.get())) {
				
				ResponseEntity <Board> response = validateMove(game.get(),board.get(), player.get(), position); 
				if (response == null) {
					boolean isPlayerOne = (player.get().getId().equals(game.get().getPlayerOne().getId()));
					resultBoard = playService.movePlay(board.get(), isPlayerOne, position);
					if (resultBoard == null) return ResponseEntity.badRequest().build();
					
					if (playService.checkGameOver(board.get())) {
						playService.finishGame(board.get().getGame());
						webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.END,
																	"The game ends."));
					} else {
						GameRules gameRules = new GameRules();
						if(!gameRules.checkExtraMove(isPlayerOne, board.get().getPits())){
							gameService.changeTurn(game.get());
						}
						webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_BOARD,
													"It is the turn of " + game.get().getTurnOfWithId().getName()));

					}
					 boardService.updateBoard(board.get());
					return ResponseEntity.ok(resultBoard);
				}
			}			
		}
		return ResponseEntity.badRequest().build();
	}
	@GetMapping(value="/board/{gameId}")
    public Board getBoard(@PathVariable Long gameId) {

        // Get info
        Game game = gameService.findById(gameId).get();
        Board board = boardService.getBoardByGame(game).get();

        // Return board
        return board;
    }


	public ResponseEntity<Board> validateMove (Game game, Board board, Player player, Integer position) {

		if (!MoveValidationUtil.isMyTurn(game, player))
			throw new ResourceException(HttpStatus.BAD_REQUEST, "It is not your turn.");
		if (game.getPlayerOne().getId().equals(game.getPlayerTwo().getId()))
			throw new ResourceException(HttpStatus.BAD_REQUEST, "You need an opponent.");

		if (game.getPlayerOne().getId().equals(game.getTurnOfWithId().getId())
				&& (position < PlayService.PIT_0_PLAYER_ONE || position >= PlayService.KALAHA_PLAYER_ONE))
			throw new ResourceException(HttpStatus.BAD_REQUEST, "Your pits are on the top row.");
		else if (game.getPlayerTwo().getId().equals(game.getTurnOfWithId().getId())
				&& (position < PlayService.PIT_0_PLAYER_TWO || position >= PlayService.KALAHA_PLAYER_TWO))
			throw new ResourceException(HttpStatus.BAD_REQUEST, "Your pits are on the bottom row.");

		return null;
	}
}

