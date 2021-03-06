package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.bol.kalaha.util.GameConstantsEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class GameRulesServiceTest {

    final GameRulesService gameRulesService = new GameRulesService();
    Board board;

    @Test
    @DisplayName("Test pit sowing.")
    void sowPit() {
        setUp();
        // PLAYER 1's TURN
        int positionOfPitToPlay = 1;
        gameRulesService.sowPit(board, positionOfPitToPlay, true);
        List<Pit> pits = board.getPits();
        assertEquals(0, pits.get(positionOfPitToPlay - 1).getValue());
        assertEquals(1, pits.get(KALAHA_PLAYER_ONE.getValue() - 1).getValue());

        positionOfPitToPlay = 2;
        gameRulesService.sowPit(board, positionOfPitToPlay, true);
        pits = board.getPits();
        assertEquals(0, pits.get(positionOfPitToPlay - 1).getValue());
        assertEquals(2, pits.get(KALAHA_PLAYER_ONE.getValue() - 1).getValue());

        // PLAYER 2's TURN
        positionOfPitToPlay = 13;
        gameRulesService.sowPit(board, positionOfPitToPlay, false);
        pits = board.getPits();
        System.out.println(Arrays.toString(pits.toArray()));
        assertEquals(0, pits.get(positionOfPitToPlay - 1).getValue());
        assertEquals(1, pits.get(KALAHA_PLAYER_TWO.getValue() - 1).getValue());
    }

    void setUp() {
        board = new Board();
        board.setId(1L);
        List<Pit> pits = new ArrayList<>();
        for (int i = 1; i <= KALAHA_PLAYER_TWO.getValue(); i++) {
            if (i == KALAHA_PLAYER_ONE.getValue() || i == KALAHA_PLAYER_TWO.getValue()) {
                Pit kalaha = new Pit();
                kalaha.setPosition(i);
                kalaha.setValue(EMPTY_KALAHA.getValue());
                pits.add(kalaha);
            } else {
                Pit pit = new Pit();
                pit.setPosition(i);
                pit.setValue(NUMBER_OF_STONES.getValue());
                pits.add(pit);

            }
        }
        board.setPits(pits);
    }

    @Test
    @DisplayName("Test the check for capture.")
    void checkAndCapture() {
        //PLAYER 1's MOVE
        boolean isPlayerOne = true;
        int theLastPosition = 3;
        Pit[] pitsArray = new Pit[14];

        pitsArray[KALAHA_PLAYER_ONE.getValue() - 1] = new Pit();
        pitsArray[KALAHA_PLAYER_ONE.getValue() - 1].setValue(0);

        pitsArray[theLastPosition - 1] = new Pit();
        pitsArray[theLastPosition - 1].setValue(1);

        pitsArray[gameRulesService.getOpponentIndex(theLastPosition)] = new Pit();
        pitsArray[gameRulesService.getOpponentIndex(theLastPosition)].setValue(9);

        List<Pit> pits = Arrays.asList(pitsArray);
        Board board = new Board();
        board.setPits(pits);
        gameRulesService.checkAndCapture(isPlayerOne, theLastPosition, board);
        assertEquals(10, pits.get(KALAHA_PLAYER_ONE.getValue() - 1).getValue());

        //PLAYER 2's MOVE
        isPlayerOne = false;
        theLastPosition = 10;
        pitsArray = new Pit[14];

        pitsArray[KALAHA_PLAYER_TWO.getValue() - 1] = new Pit();
        pitsArray[KALAHA_PLAYER_TWO.getValue() - 1].setValue(0);

        pitsArray[theLastPosition - 1] = new Pit();
        pitsArray[theLastPosition - 1].setValue(1);

        pitsArray[gameRulesService.getOpponentIndex(theLastPosition)] = new Pit();
        pitsArray[gameRulesService.getOpponentIndex(theLastPosition)].setValue(9);

        pits = Arrays.asList(pitsArray);
        new Board();
        board.setPits(pits);
        gameRulesService.checkAndCapture(isPlayerOne, theLastPosition, board);
        assertEquals(10, pits.get(KALAHA_PLAYER_TWO.getValue() - 1).getValue());

    }

    @Test
    @DisplayName("Test the check for extra move.")
    void checkExtraMove() {
        boolean isPlayerOne = true;
        int theLastPosition = KALAHA_PLAYER_ONE.getValue();

        assertTrue(gameRulesService.checkExtraMove(isPlayerOne, theLastPosition));


        isPlayerOne = true;
        theLastPosition = 3;

        assertFalse(gameRulesService.checkExtraMove(isPlayerOne, theLastPosition));


        isPlayerOne = false;
        theLastPosition = KALAHA_PLAYER_ONE.getValue();

        assertFalse(gameRulesService.checkExtraMove(isPlayerOne, theLastPosition));


        isPlayerOne = false;
        theLastPosition = KALAHA_PLAYER_TWO.getValue();

        assertTrue(gameRulesService.checkExtraMove(isPlayerOne, theLastPosition));
    }


    @Test
    @DisplayName("Test the check for game over.")
    void checkGameOver(){

        Pit[] pitsArray = Stream.generate(Pit::new).limit(14).toArray(Pit[]::new);

        pitsArray[KALAHA_PLAYER_ONE.getValue()-1].setValue(40);
        pitsArray[KALAHA_PLAYER_TWO.getValue()-1].setValue(17);

        pitsArray[7].setValue(1);
        pitsArray[8].setValue(14);

        Board board = new Board();
        board.setPits(Arrays.asList(pitsArray));

        assertTrue(gameRulesService.checkGameOver(board));

        assertEquals(32,board.getPits().get(KALAHA_PLAYER_TWO.getValue()-1).getValue());

        assertEquals(0,board.getPits().get(7).getValue());
        assertEquals(0,board.getPits().get(8).getValue());

    }

    @Test
    void getOpponentIndex() {
         assertEquals(KALAHA_PLAYER_ONE.getValue(), gameRulesService.getOpponentIndex(KALAHA_PLAYER_ONE.getValue()-1));
    }

    @Test
    void changeTurn() {
        Game game = new Game();

        Player player1 = new Player();
        player1.setId(1L);
        Player player2 = new Player();
        player2.setId(2L);
        game.setPlayerOne(player1);
        game.setPlayerOne(player2);
        game.setTurnOf(player1);
        gameRulesService.changeTurn(game);
        assertEquals(player2, game.getTurnOf());
    }
}