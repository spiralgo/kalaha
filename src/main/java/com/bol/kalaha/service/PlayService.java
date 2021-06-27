package com.bol.kalaha.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;

@Service
@Transactional
public class PlayService {
	public static final Integer PIT_0_PLAYER_ONE = 1;
	public static final Integer PIT_0_PLAYER_TWO = 8;
	public static final Integer KALAHA_PLAYER_ONE = 7;
	public static final Integer KALAHA_PLAYER_TWO = 14;
	public static final Integer ZERO = 0;

	@Autowired
	private GameService gameService;

	@Autowired
	private PitService pitService;

	public PlayService() {
		// TODO Auto-generated constructor stub
	}

	public Board movePlay(Board board, Player player, Integer position) {

		if (isMyTurn(board.getGame(), player)) {
			sowPit(board, position);
		} else
			return null;
		return board;

	}

	public boolean isMyTurn(Game game, Player player) {
		boolean result = game.getTurnOfWithId().equals(player);
		return result;
	}

	public boolean checkGameOver(Board board) {
		int begin, end;
		if (isMyTurn(board.getGame(), board.getGame().getPlayerOne())) {
			begin = PIT_0_PLAYER_ONE;
			end = KALAHA_PLAYER_ONE;
		} else {
			begin = PIT_0_PLAYER_TWO;
			end = KALAHA_PLAYER_TWO;
		}
		for (int i = begin; i < end; i++) {
			if (pitService.getPitNumberOfStonesByBoardAndPosition(board, i) > 0) {
				return false;
			}
		}
		return true;

	}

	public Integer sowPit(Board board, Integer position) {
		int numberOfStones = pitService.getPitNumberOfStonesByBoardAndPosition(board, position);

		pitService.updatePitNumberOfStones(board, position, ZERO, false); // It's not for sum, it's to set Zero
		while (numberOfStones > 0) {
			position++;
			if (position > KALAHA_PLAYER_TWO)
				position = PIT_0_PLAYER_TWO;
			else if (position == PIT_0_PLAYER_TWO)
				position = PIT_0_PLAYER_ONE;
			pitService.updatePitIncrement(board, position);
			numberOfStones--;
		}
		if (position != KALAHA_PLAYER_ONE && position != KALAHA_PLAYER_TWO) {
			checkCapture(board, position);
			gameService.saveGame(gameService.changeTurn(board.getGame()));
		}

		return position;

	}

	public void checkCapture(Board board, Integer position) {
		if (pitService.getPitNumberOfStonesByBoardAndPosition(board, position) - 1 == 0) {
			if (position >= PIT_0_PLAYER_TWO) {
				pitService.updatePitStealPit(board, position, position - (KALAHA_PLAYER_ONE));
			} else {
				pitService.updatePitStealPit(board, position, KALAHA_PLAYER_ONE + position);
			}
		}
	}

	public void finishGame(Game game) {
		gameService.finishGame(game);
	}

}
