package com.bol.kalaha.service;

import static org.junit.jupiter.api.Assertions.*;
import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.util.JoinAGameValidationEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameRulesService gameRulesService;
    @Mock
    private BoardService boardService;

    private GameService gameService;
    private Game game;

    @BeforeEach
    void setUp()
    {
        game = new Game();
        game.setId(1L);
        gameService = new GameService(gameRepository,
                                      gameRulesService,
                                      boardService);
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
    void getGames() {
        doReturn(Collections.singletonList(game))
                .when(gameRepository)
                .findAllByOrderByIdDesc();

        assertThat(gameService.getGames().size(),  Matchers.equalTo(1));
    }

    @Test
    void generateMessageFromAnswer() throws KalahaException {

        Exception exception = assertThrows(KalahaException.class, () -> {
            gameService.generateMessageFromAnswer(JoinAGameValidationEnum.NEED_TO_CREATE_A_PLAYER
                        , null);

        });
        assertEquals(SHOULD_CREATE_PLAYER.getValue(), exception.getMessage());

        Player test = new Player();
        test.setName("test");
        String message = gameService.generateMessageFromAnswer(JoinAGameValidationEnum.JOIN_AS_THE_PLAYER_TWO
                    , test);
        assertEquals(message, test.getName() + JOINS_OPPONENT.getValue());


        message = gameService.generateMessageFromAnswer(JoinAGameValidationEnum.ALREADY_A_PLAYER
                 , test);
        assertEquals(message, test.getName() + REJOINS.getValue());

        message = gameService.generateMessageFromAnswer(JoinAGameValidationEnum.JOIN_AS_A_WIEVER
                 , test);
        assertEquals(message, test.getName() + JOINS_VIEWER.getValue());

    }

    @Test
    void startJoinProcess() throws KalahaException {

        Exception exception = assertThrows(KalahaException.class, () -> {
            gameService.startJoinProcess(null, null);

        });
        assertEquals(GAME_NOT_FOUND.getValue(), exception.getMessage());

        doReturn(Optional.of(game))
                .when(gameRepository)
                .findById(ArgumentMatchers.anyLong());

        Player playerOne = new Player();
        playerOne.setId(1L);
        game.setPlayerOne(playerOne);

        doReturn(game)
                .when(gameRepository)
                .save(ArgumentMatchers.any(Game.class));


        Game game = new Game();
        Player playerTwo = new Player();
        playerTwo.setName("test");
        playerTwo.setId(2L);

        game.setPlayerOne(playerOne);


       Optional<ResponseData<Game>> responseDataOptional = gameService.startJoinProcess(1L, playerTwo);
        assertEquals(playerTwo.getName() + JOINS_OPPONENT.getValue(),
                responseDataOptional.get().getMessage());


    }

}