package com.bol.kalaha.util;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;

public class MoveValidationUtil {
    public static boolean isMyTurn(Game game, Player player) {
        boolean result = game.getTurnOf().equals(player);
        return result;
    }
}
