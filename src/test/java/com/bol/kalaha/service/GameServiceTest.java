package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    private GameService gameService;
    private Game game;

    @BeforeEach
    void setUp()
    {
        game = new Game();
        game.setId(1L);
        gameService = new GameService(gameRepository);
    }

    @Test
    void createNewGame() {

        doReturn(game)
                .when(gameRepository)
                .save(ArgumentMatchers.any(Game.class));

        Game test = new Game();
        assertThat(gameService.createNewGame(test).getId(),
                Matchers.equalTo(1L));
    }

    @Test
    void findById() {

        doReturn(Optional.of(game))
                .when(gameRepository)
                .findById(ArgumentMatchers.anyLong());

        assertThat(gameService.findById(1L).get().getId(),
                Matchers.equalTo(1L));
    }

    @Test
    void joinGame() {
        Player playerOne = new Player();
        playerOne.setId(1L);
        game.setPlayerOne(playerOne);

        doReturn(game)
                .when(gameRepository)
                .save(ArgumentMatchers.any(Game.class));

        Game test = new Game();
        assertThat(gameService.joinGame(test).getPlayerOne().getId(),
                Matchers.equalTo(1L));
    }

    @Test
    void finishGame() {
        doReturn(game)
                .when(gameRepository)
                .save(ArgumentMatchers.any(Game.class));

        assertThat(gameService.finishGame(game).isOver(),
                Matchers.equalTo(true));
    }

    @Test
    void getGamesToJoin() {
        doReturn(Collections.singletonList(game))
                .when(gameRepository)
                .findAllByOrderByIdDesc();

        assertThat(gameService.getGames().size(),  Matchers.equalTo(1));
    }

}