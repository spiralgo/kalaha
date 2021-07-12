package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bol.kalaha.util.GameConstantsEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveServiceTest {
    GameRulesService gameRulesService = new GameRulesService();
    MoveService moveService = new MoveService(gameRulesService);
    Game game;
    Player playerOne;
    Player playerTwo;
    Board board;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerOne = new Player();
        playerOne.setId(1L);
        playerTwo = new Player();
        playerTwo.setId(2L);
    }
    void prepareBoard() {
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
    void movePlay() {
        prepareBoard();
        assertEquals(KALAHA_PLAYER_ONE.getValue(), moveService.movePlay(board, true, FIRST_PIT_POS_PLAYER_ONE.getValue()));

        moveService.movePlay(board, true, FIRST_PIT_POS_PLAYER_ONE.getValue()+1);
        assertEquals(board.getPits().get(KALAHA_PLAYER_ONE.getValue()-1).getValue(), 2);

    }

}