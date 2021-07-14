package com.bol.kalaha.resource;

import com.bol.kalaha.config.WebSocketResource;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static com.bol.kalaha.util.JsonUtil.asJsonString;
import static com.bol.kalaha.util.MessagesEnum.SHOULD_CREATE_PLAYER;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameResource.class)
class GameResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    GameService gameService;
    @MockBean
    WebSocketResource webSocketResource;

    @Test
    void createNewGame() throws Exception {
        Player player = new Player();
        String playerJson = asJsonString(player);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/game/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .string(SHOULD_CREATE_PLAYER.getValue());
        mockMvc.perform(builder).andExpect(contentMatcher).
                andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


        player.setId(1L);
        playerJson = asJsonString(player);
        builder = MockMvcRequestBuilders
                .post("/game/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

          mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.OK.value()));

    }

    @Test
    void joinGame() throws Exception {
        Player player = new Player();
        String playerJson = asJsonString(player);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .patch("/game/join/1")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.OK.value()));

    }

    @Test
    void getGamesToJoin() throws Exception {
       List<Game> games = Collections.singletonList(new Game());
       Mockito.when(gameService.getGames()).thenReturn(games);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/game")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}