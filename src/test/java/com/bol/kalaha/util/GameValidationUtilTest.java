package com.bol.kalaha.util;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameValidationUtilTest {
    @Test
    @DisplayName("Validate joining a game room.")
    void validateJoin() {
        Game game = new Game();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setId(1L);

        game.setPlayerOne(player1);


        Player player = new Player();
        assertEquals(JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER,
                GameValidationUtil.validateJoin(game, player));
        player.setId(1L);
        assertEquals(JoinAGameValidationEnum.ALREADY_A_PLAYER,
                GameValidationUtil.validateJoin(game, player));

        player.setId(3L);
        assertEquals(JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO,
                GameValidationUtil.validateJoin(game, player));

        player2.setId(2L);
        game.setPlayerTwo(player2);
        assertEquals(JoinAGameValidationEnum.JOIN_AS_A_WIEVER,
                GameValidationUtil.validateJoin(game, player));

    }

}