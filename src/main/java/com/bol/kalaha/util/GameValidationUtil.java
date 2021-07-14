package com.bol.kalaha.util;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;

import static com.bol.kalaha.util.JoinAGameValidationEnum.*;

public class GameValidationUtil {
    public static JoinAGameValidationEnum validateJoin(Game game, Player player) throws KalahaException {

        if (player.getId() == null){
            return NEED_TO_CREATE_A_PLAYER;
        }

        JoinAGameValidationEnum result;
        Player playerOne = game.getPlayerOne();
        Player playerTwo = game.getPlayerTwo();

        if (player.equals(playerOne) || player.equals(playerTwo)){
            result = ALREADY_A_PLAYER;
        }else if(playerTwo == null){
            result = JOIN_AS_THE_PLAYER_TWO;
        }else {
            result = JOIN_AS_A_WIEVER;

        }
        return result;
    }
}
