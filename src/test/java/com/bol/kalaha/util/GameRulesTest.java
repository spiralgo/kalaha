package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameRulesTest {
    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;
    GameRules gameRules = new GameRules();
    Board board;

    @Test
    void sowPit() {
        setUp();
        // PLAYER 1's TURN
         int positionOfPitToPlay = 1;
         gameRules.sowPit(board, positionOfPitToPlay);
         List<Pit> pits = board.getPits();
         assertEquals(0, pits.get(positionOfPitToPlay-1).getValue());
         assertEquals(1, pits.get(KALAHA_PLAYER_ONE-1).getValue());

        positionOfPitToPlay = 2;
        gameRules.sowPit(board, positionOfPitToPlay);
        pits = board.getPits();
        assertEquals(0, pits.get(positionOfPitToPlay-1).getValue());
        assertEquals(2, pits.get(KALAHA_PLAYER_ONE-1).getValue());

        // PLAYER 2's TURN
        positionOfPitToPlay = 13;
        gameRules.sowPit(board, positionOfPitToPlay);
        pits = board.getPits();
        System.out.println(Arrays.toString(pits.toArray()));
        //TODO:  pits.forEach(a -> System.out.println(a.getValue()));
         assertEquals(0, pits.get(positionOfPitToPlay-1).getValue());
        assertEquals(1, pits.get(KALAHA_PLAYER_TWO-1).getValue());
    }

    void setUp(){
        board = new Board();
        board.setId(1L);
        List<Pit> pits = new ArrayList<>();
        for (int i = 1; i <=KALAHA_PLAYER_TWO; i++){
            if(i == KALAHA_PLAYER_ONE || i == KALAHA_PLAYER_TWO){
                pits.add(new Pit(board, i, 0));
            } else {
                pits.add(new Pit(board, i, 6));

            }
        }
        board.setPits(pits);
    }
    @Test
    void checkAndCapture(){
        //PLAYER 1's MOVE
        boolean isPlayerOne = true;
        int theLastPosition = 3;
        Pit[] pitsArray = new Pit[14];

        pitsArray[KALAHA_PLAYER_ONE-1] = new Pit();
        pitsArray[KALAHA_PLAYER_ONE-1].setValue(0);

        pitsArray[theLastPosition-1] = new Pit();
        pitsArray[theLastPosition-1].setValue(1);

        pitsArray[gameRules.getOpponentIndex(theLastPosition)] = new Pit();
        pitsArray[gameRules.getOpponentIndex(theLastPosition)].setValue(9);

        List<Pit> pits = Arrays.asList(pitsArray);
        Board board = new Board();
        board.setPits(pits);
        gameRules.checkAndCapture(isPlayerOne, theLastPosition, board);
        assertEquals(10, pits.get(KALAHA_PLAYER_ONE-1).getValue());

        //PLAYER 2's MOVE
        isPlayerOne = false;
        theLastPosition = 10;
        pitsArray = new Pit[14];

        pitsArray[KALAHA_PLAYER_TWO-1] = new Pit();
        pitsArray[KALAHA_PLAYER_TWO-1].setValue(0);

        pitsArray[theLastPosition-1] = new Pit();
        pitsArray[theLastPosition-1].setValue(1);

        pitsArray[gameRules.getOpponentIndex(theLastPosition)] = new Pit();
        pitsArray[gameRules.getOpponentIndex(theLastPosition)].setValue(9);

        pits = Arrays.asList(pitsArray);
        new Board();
        board.setPits(pits);
        gameRules.checkAndCapture(isPlayerOne, theLastPosition, board);
        assertEquals(10, pits.get(KALAHA_PLAYER_TWO-1).getValue());

    }
    @Test
    void checkExtraMove(){
        boolean isPlayerOne = true;
        Pit[] pitsArray = new Pit[14];

        pitsArray[KALAHA_PLAYER_ONE-1] = new Pit();
        pitsArray[KALAHA_PLAYER_ONE-1].setValue(1);

        pitsArray[KALAHA_PLAYER_TWO-1] = new Pit();
        pitsArray[KALAHA_PLAYER_TWO-1].setValue(1);
        List<Pit> pits = Arrays.asList(pitsArray);

        assertTrue(gameRules.checkExtraMove(isPlayerOne, pits));

    }
}