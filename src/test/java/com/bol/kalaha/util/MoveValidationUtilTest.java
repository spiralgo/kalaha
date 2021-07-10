package com.bol.kalaha.util;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.bol.kalaha.util.GameConstantsEnum.*;
import static com.bol.kalaha.util.MessagesEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class MoveValidationUtilTest {
    Game game;
    Player playerOne;
    Player playerTwo;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerOne = new Player();
        playerOne.setId(1L);
        playerTwo = new Player();
        playerTwo.setId(2L);
    }

    @Test
    void isPlayersTurn() {
        game.setTurnOf(playerOne);
        assertTrue(MoveValidationUtil.isPlayersTurn(game, playerOne));
    }

    @Test
    void isWrongPit() {
        game.setPlayerTwo(playerTwo);
        game.setTurnOf(playerTwo);
        assertTrue(MoveValidationUtil.isWrongPit(game,
                                                        playerTwo,
                                                 KALAHA_PLAYER_ONE.getValue()-1));
    }

    @Test
    void validateMove() {
        game.setPlayerOne(playerOne);
        game.setPlayerTwo(playerTwo);

        game.setTurnOf(playerOne);
        game.setBoard(new Board());
        game.getBoard().setPits(new ArrayList<>());
        game.getBoard().getPits().add(new Pit());
        game.getBoard().getPits().get(PIT_0_PLAYER_ONE.getValue()-1).setValue(0);
        Exception exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game,
                    playerOne,
                    PIT_0_PLAYER_ONE.getValue());
        });
        assertEquals(PIT_IS_EMPTY.getValue(), exception.getMessage());


        game.setTurnOf(playerTwo);
         exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game,
                    playerTwo,
                    KALAHA_PLAYER_ONE.getValue()-1);
        });
        assertEquals(YOUR_PITS_BOTTOM.getValue(), exception.getMessage());


        game.setTurnOf(playerOne);
        exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game,
                                            playerOne,
                                            KALAHA_PLAYER_TWO.getValue()-1);
        });
        assertEquals(YOUR_PITS_TOP.getValue(), exception.getMessage());


        game.setPlayerTwo(playerTwo);
        game.setTurnOf(playerTwo);
        exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game, playerOne,1);
        });
        assertEquals(NOT_YOUR_TURN.getValue(), exception.getMessage());


        game.setPlayerTwo(null);
        exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game, playerOne,1);
        });
        assertEquals(NEED_OPPONENT.getValue(), exception.getMessage());


        game.setOver(true);
        exception = assertThrows(ResourceException.class, () -> {
            MoveValidationUtil.validateMove(game, playerOne,1);
        });
        assertEquals(GAME_OVER.getValue(), exception.getMessage());

    }
}