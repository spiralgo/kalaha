package com.bol.kalaha.util;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import org.springframework.http.HttpStatus;

import static com.bol.kalaha.util.GameConstantsEnum.*;
import static com.bol.kalaha.util.MessagesEnum.*;

public class MoveValidationUtil {

    public static boolean isPlayersTurn(Game game, Player player) {
        boolean result = game.getTurnOf().equals(player);
        return result;
    }
    public static boolean isWrongPit(Game game, Player player, int position) {
        boolean result = false;
        boolean isPlayerOne = player.equals(game.getPlayerOne());
        if (isPlayerOne && isPlayersTurn(game, player)
                && (position < PIT_0_PLAYER_ONE.getValue()
                || position >= KALAHA_PLAYER_ONE.getValue())){
            result = true;
        }else if (!isPlayerOne && isPlayersTurn(game, player)
                && (position < PIT_0_PLAYER_TWO.getValue()
                || position >= KALAHA_PLAYER_TWO.getValue())){
            result = true;
        }

        return result;
    }
    public static boolean validateMove(Game game, Player player, Integer position) {
        String message = "";

        if (game.isOver()) {
            message = GAME_OVER.getValue();
        } else if (game.getPlayerTwo() == null) {
            message = NEED_OPPONENT.getValue();
        } else if (!isPlayersTurn(game, player)) {
            message = NOT_YOUR_TURN.getValue();
        } else if (isWrongPit(game, game.getPlayerOne(),
                position)) {
            message = YOUR_PITS_TOP.getValue();
        } else if (isWrongPit(game, game.getPlayerTwo(),
                position)) {
            message = YOUR_PITS_BOTTOM.getValue();
        } else if (game.getBoard().getPits().get(position - 1).getValue() == 0) {
            message = PIT_IS_EMPTY.getValue();
        }

        if (message.length() > 0)
            throw new ResourceException(HttpStatus.BAD_REQUEST, message);


        return true;
    }

}
