package com.bol.kalaha.service;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.util.GameRulesService;
import com.bol.kalaha.util.MoveValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MoveService {
    public static final Integer PIT_0_PLAYER_ONE = 1;
    public static final Integer PIT_0_PLAYER_TWO = 8;
    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;

    GameRulesService gameRulesService;

    @Autowired
    MoveService(GameRulesService gameRulesService){
        this.gameRulesService = gameRulesService;
    }

    public int movePlay(Board board, boolean isPlayerOne, Integer position) {
        int theLastPosition = gameRulesService.sowPit(board, position, isPlayerOne);
        gameRulesService.checkAndCapture(isPlayerOne, theLastPosition, board);
        return theLastPosition;
    }

    public boolean validateMove(Game game, Player player, Integer position) {
        String message = "";

        if (game.isOver()){
            message = "The game is over.";
        }else if (game.getPlayerTwo() == null){
            message = "You need an opponent.";
        }else if (!MoveValidationUtil.isMyTurn(game, player)){
            message = "It is not your turn.";
        }else if (game.getTurnOf().equals(game.getPlayerOne())
                && (position < MoveService.PIT_0_PLAYER_ONE
                             || position >= MoveService.KALAHA_PLAYER_ONE)){
            message = "Your pits are on the top row.";
        } else if (game.getTurnOf().equals(game.getPlayerTwo())
                && (position < MoveService.PIT_0_PLAYER_TWO
                             || position >= MoveService.KALAHA_PLAYER_TWO)){
            message = "Your pits are on the bottom row.";
        } else if (game.getBoard().getPits().get(position-1).getValue()==0){
            message=  "This pit is empty.";
        }

        if(message.length()>0)
            throw new ResourceException(HttpStatus.BAD_REQUEST,  message);


        return true;
    }
}
