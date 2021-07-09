package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.util.GameRules;
import org.springframework.stereotype.Service;

@Service
public class MoveService {
    public static final Integer PIT_0_PLAYER_ONE = 1;
    public static final Integer PIT_0_PLAYER_TWO = 8;
    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;

    public int movePlay(Board board, boolean isPlayerOne, Integer position) {
        GameRules gameRules = new GameRules();
        int theLastPosition = gameRules.sowPit(board, position, isPlayerOne);
        gameRules.checkAndCapture(isPlayerOne, theLastPosition, board);
        return theLastPosition;
    }
}
