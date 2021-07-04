package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;

import java.util.ArrayList;
import java.util.List;

public class BoardUtil {
    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;
    public static final Integer NUMBER_OF_STONES = 6;
    public static final Integer EMPTY_KALAHA = 0;

    public static Board initiateABoard() {
        Board board = new Board();

        List<Pit> pits = new ArrayList<>();
        for (int i = 1; i <= KALAHA_PLAYER_TWO; i++) {
            if (i == KALAHA_PLAYER_ONE || i == KALAHA_PLAYER_TWO) {
                Pit kalaha = new Pit();
                kalaha.setPosition(i);
                kalaha.setValue(EMPTY_KALAHA);
                kalaha.setBoard(board);
                pits.add(kalaha);
            } else {
                Pit pit = new Pit();
                pit.setPosition(i);
                pit.setValue(NUMBER_OF_STONES);
                pit.setBoard(board);
                pits.add(pit);

            }
        }
        board.setPits(pits);
        return board;
    }

}
