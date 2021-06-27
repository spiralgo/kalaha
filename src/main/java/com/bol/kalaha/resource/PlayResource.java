package com.bol.kalaha.resource;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
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
					resultBoard = playService.movePlay(board.get(), player.get(), position);
					if (resultBoard == null) return ResponseEntity.badRequest().build();
					
					if (playService.checkGameOver(board.get())) {
						playService.finishGame(board.get().getGame());
						webSocketResource.publishWebSocket("end");
					} else 
						webSocketResource.publishWebSocket("update");
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
		if (game.getPlayerOne().equals(game.getPlayerTwo())) return ResponseEntity.badRequest().build();
		if (game.getPlayerOne().equals(game.getTurnOfWithId()) && (position < PlayService.PIT_0_PLAYER_ONE || position >= PlayService.KALAHA_PLAYER_ONE))
			return ResponseEntity.badRequest().build();
		else if (game.getPlayerTwo().equals(game.getTurnOfWithId()) && (position < PlayService.PIT_0_PLAYER_TWO || position >= PlayService.KALAHA_PLAYER_TWO))
			return ResponseEntity.badRequest().build();
		return null;
	}
}

