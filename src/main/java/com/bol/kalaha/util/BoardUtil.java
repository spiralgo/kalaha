package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;

import java.util.ArrayList;
import java.util.List;

import static com.bol.kalaha.util.GameConstantsEnum.*;

public class BoardUtil {

    public static Board initiateABoard() {
        Board board = new Board();

        List<Pit> pits = new ArrayList<>();
        for (int i = 1; i <= KALAHA_PLAYER_TWO.getValue(); i++) {
            if (i == KALAHA_PLAYER_ONE.getValue()
                    || i == KALAHA_PLAYER_TWO.getValue()) {
                Pit kalaha = new Pit();
                kalaha.setPosition(i);
                kalaha.setValue(EMPTY_KALAHA.getValue());
                kalaha.setBoard(board);
                pits.add(kalaha);
            } else {
                Pit pit = new Pit();
                pit.setPosition(i);
                pit.setValue(NUMBER_OF_STONES.getValue());
                pit.setBoard(board);
                pits.add(pit);

            }
        }
        board.setPits(pits);
        return board;
    }

}
