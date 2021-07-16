package com.bol.kalaha.service;

import com.bol.kalaha.exception.KalahaException;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import com.bol.kalaha.util.MoveValidationUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bol.kalaha.util.MessagesEnum.GAME_OVER;
import static com.bol.kalaha.util.MessagesEnum.PLAYER_ALREADY_EXISTS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    private PlayerService playerService;
    private Player player;

    @BeforeEach
    void setUp() {

        player = new Player();
        player.setId(1L);
        player.setName("PlayerOneActual");

        playerService = new PlayerService(playerRepository);
     }

    @Test
    @DisplayName("Creating a new player.")
    void createPlayer() throws KalahaException {
        doReturn(Optional.of(player))
                .when(playerRepository)
                .findByName(ArgumentMatchers.any(String.class));


        doReturn(player)
                .when(playerRepository)
                .save(ArgumentMatchers.any(Player.class));

        Player test = new Player();
        test.setName("PlayerOneActual");

        Exception exception = assertThrows(KalahaException.class, () -> {
            playerService.createPlayer(test);
        });
        assertEquals(PLAYER_ALREADY_EXISTS.getValue(), exception.getMessage());

        doReturn(Optional.ofNullable(null))
                .when(playerRepository)
                .findByName(ArgumentMatchers.any(String.class));

        assertThat(playerService.createPlayer(test).getName(),
                Matchers.equalTo("PlayerOneActual"));
    }
    @Test
    @DisplayName("Find a player by id.")
    void findById(){
        doReturn(Optional.of(player))
                .when(playerRepository)
                .findById(ArgumentMatchers.anyLong());

        assertThat(playerService.findById(1L).get().getName(),
                Matchers.equalTo("PlayerOneActual"));
    }
}