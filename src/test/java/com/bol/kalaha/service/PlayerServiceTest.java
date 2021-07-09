package com.bol.kalaha.service;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.PlayerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
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
    void createPlayer(){
        doReturn(player)
                .when(playerRepository)
                .save(ArgumentMatchers.any(Player.class));

        Player test = new Player();
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