package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.util.GameRulesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveServiceTest {
    GameRulesService gameRulesService = new GameRulesService();
    MoveService moveService = new MoveService(gameRulesService);
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
    void movePlay() {
    }

}