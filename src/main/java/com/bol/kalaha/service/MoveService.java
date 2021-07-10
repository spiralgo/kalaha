package com.bol.kalaha.service;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.util.GameRulesService;
import com.bol.kalaha.util.MessagesEnum;
import com.bol.kalaha.util.MoveValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    GameRulesService gameRulesService;

    @Autowired
    MoveService(GameRulesService gameRulesService) {
        this.gameRulesService = gameRulesService;
    }

    public int movePlay(Board board, boolean isPlayerOne, Integer position) {
        int theLastPosition = gameRulesService.sowPit(board, position, isPlayerOne);
        gameRulesService.checkAndCapture(isPlayerOne, theLastPosition, board);
        return theLastPosition;
    }


}
