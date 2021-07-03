package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PitService;
import com.bol.kalaha.util.BoardUtil;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping ("/game")
public class GameResource {

	@Autowired
	private GameService gameService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private PitService pitService;
	@Autowired
    private WebSocketResource webSocketResource;
	
	@PostMapping(value="/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Game> createNewGame(@RequestBody Player playerOne, HttpServletResponse response) {
		Game createdGame= new Game();
		createdGame.setPlayerOne(playerOne);
		Board board = BoardUtil.initiateABoard();
		board.setGame(createdGame);
		createdGame.setBoard(board);
		createdGame.setTurnOfWithId(playerOne);
		gameService.createNewGame(createdGame);
		webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME_LIST,
				"Game #"+ createdGame.getId() +" is created by " + playerOne.getName()));

		return ResponseEntity.ok(createdGame);
		
	}
	
	
	@GetMapping(value="/{gameId}")
	@ResponseBody
 	public ResponseEntity<Optional<Game>> findGame(@PathVariable Long gameId) {
		Optional<Game> game = gameService.findById(gameId);
		
		if (game.isPresent()) {
			return ResponseEntity.ok(game);
		}
		return ResponseEntity.notFound().build();	
	}
	
	@PatchMapping(value="/join/{gameId}")
	@ResponseBody
 	public ResponseEntity<Game> joinGame(@PathVariable Long gameId, @RequestBody Player player) {
		Optional<Game> game = gameService.findById(gameId);		
		ResponseEntity <Game> answer = validateJoin(game, player);
		if (answer == null) {
			Game savedGame = game.get();
			savedGame.setPlayerTwo(player);	

			return ResponseEntity.ok(gameService.joinGame(savedGame));
		}
		
		return answer;		
	}
	@GetMapping
	@ResponseBody
    public List<Game> getGamesToJoin() {
		List <Game> games = gameService.getGamesToJoin();
        return games;
    }
    

    @RequestMapping(value = "/player/{player}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getPlayerGames(Player player) {
    	List<Game> games = gameService.getPlayerGames(player);
        return games;
    }
	
	private ResponseEntity <Game> validateJoin(Optional<Game> game, Player player) {
		if (!game.isPresent())
		throw new ResourceException(HttpStatus.BAD_REQUEST, "You need to create a game first.");

		Player playerOne = game.get().getPlayerOne();
		Player playerTwo = game.get().getPlayerTwo();
		if (player.equals(playerOne)  || player.equals(playerTwo)  ||  playerTwo == null)
	 		return ResponseEntity.ok(game.get());

		return null;
	}
	
}
