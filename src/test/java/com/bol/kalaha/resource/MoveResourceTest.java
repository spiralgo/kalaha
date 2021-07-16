package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.*;
import com.bol.kalaha.util.BoardUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.bol.kalaha.util.GameConstantsEnum.FIRST_PIT_POS_PLAYER_ONE;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoveResource.class)
class MoveResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    GameService gameService;
    @MockBean
    PlayerService playerService;
    @MockBean
    MoveService moveService;
    @MockBean
    WebSocketResource webSocketResource;
    @Test
    void movePlay() throws Exception {
        Player playerOne = new Player();
        playerOne.setId(1L);
        doReturn(Optional.of(playerOne))
                .when(playerService)
                .findById(ArgumentMatchers.anyLong());


        Game game = new Game();
        Player playerTwo = new Player();
        game.setPlayerOne(playerOne);
        game.setPlayerTwo(playerTwo);
        game.setTurnOf(playerOne);
        game.setId(1L);
        Board board = BoardUtil.initiateABoard();
        game.setBoard(board);
        doReturn(Optional.of(game))
                .when(gameService)
                .findById(ArgumentMatchers.anyLong());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/move/"
                        + game.getId()
                        +"/" +playerOne.getId()
                        +"/"+ FIRST_PIT_POS_PLAYER_ONE.getValue())

                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.OK.value()));

    }
}