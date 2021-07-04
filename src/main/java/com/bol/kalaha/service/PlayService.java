package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.util.GameRules;
import com.bol.kalaha.util.MoveValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Board movePlay(Board board, boolean isPlayerOne, Integer position) {
        GameRules gameRules = new GameRules();
        int theLastPosition = gameRules.sowPit(board, position);
        gameRules.checkAndCapture(isPlayerOne, theLastPosition, board);
        return board;
    }

    public boolean checkGameOver(Board board) {
        int begin, end;
        if (MoveValidationUtil.isMyTurn(board.getGame(), board.getGame().getPlayerOne())) {
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


    public void finishGame(Game game) {
        gameService.finishGame(game);
    }

}
