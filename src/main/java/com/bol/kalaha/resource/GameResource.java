package com.bol.kalaha.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.util.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.BoardService;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PitService;
import com.bol.kalaha.service.PlayService;

@RestController
@RequestMapping ("/game")
public class GameResource {
	public static final Integer NUMBER_OF_STONES = 6;
	public static final Integer ZERO = 0;
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
	public ResponseEntity<Game> createNewGame (@RequestBody Player pOne, HttpServletResponse response) {
	 	Game createdGame;
		Board board;
		createdGame = gameService.createNewGame(pOne, pOne);
		board = boardService.createNewBoard(createdGame);
		// PITS PLAYER ONE
		for (int i = PlayService.PIT_0_PLAYER_ONE; i < PlayService.KALAHA_PLAYER_ONE; i++) {
			pitService.createNewPit(board, i, NUMBER_OF_STONES);
		}
		// KAHALA PLAYER ONE
		pitService.createNewPit(board, PlayService.KALAHA_PLAYER_ONE, ZERO);
		
		// PITS PLAYER ONE
		for (int i = PlayService.PIT_0_PLAYER_TWO; i < PlayService.KALAHA_PLAYER_TWO; i++) {
			pitService.createNewPit(board, i, NUMBER_OF_STONES);
		}
		// KAHALA PLAYER ONE
		pitService.createNewPit(board, PlayService.KALAHA_PLAYER_TWO, ZERO);


		webSocketResource.publishWebSocket(WebSocketUtil.getMessageJSON(WebSocketActionEnum.REFRESH_GAME_LIST,
				"Game #"+ createdGame.getId() +" is created by " + pOne.getName() ));

		return ResponseEntity.ok(createdGame);
		
	}
	
	
	@GetMapping(value="/{gameId}")
	@ResponseBody
 	public ResponseEntity<Optional<Game>> findGame (@PathVariable Long gameId) {
		Optional<Game> game = gameService.findById(gameId);
		
		if (game.isPresent()) {
			return ResponseEntity.ok(game);
		}
		return ResponseEntity.notFound().build();	
	}
	
	@PatchMapping(value="/join/{gameId}")
	@ResponseBody
 	public ResponseEntity<Game> joinGame (@PathVariable Long gameId, @RequestBody Player player) {
		Optional<Game> game = gameService.findById(gameId);		
		ResponseEntity <Game> answer = validateJoin(game, player.getId());
		if (answer == null) {
			Game savedGame = game.get();
			savedGame.setPlayerTwo(player);	
			//TODO: webSocketResource.publishWebSocket(WebSocketActionEnum.REFRESH_BOARD.getValue());
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
	
	private ResponseEntity <Game> validateJoin(Optional<Game> game, Long id) {
		if (!game.isPresent())
		throw new ResourceException(HttpStatus.BAD_REQUEST, "You need to create a game first.");

		long playerOneId = game.get().getPlayerOne().getId();
		long playerTwoId = game.get().getPlayerTwo().getId();
		if (playerOneId == id || playerTwoId == id)
			return ResponseEntity.ok(game.get());
		if (playerTwoId != id && playerOneId!=playerTwoId)
			return ResponseEntity.ok(game.get());
		
		return null;
	}
	
}
