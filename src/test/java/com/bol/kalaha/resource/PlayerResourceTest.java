package com.bol.kalaha.resource;

import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.bol.kalaha.util.JsonUtil.asJsonString;
import static com.bol.kalaha.util.JsonUtil.asObject;
import static com.bol.kalaha.util.MessagesEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerResourceTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

    }

    @Test
    void createNewPlayer() throws Exception {
        Player player = new Player();
        player.setId(1L);

        String playerJson = asJsonString(player);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/player/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .string(NONEMPTY.getValue());
        mockMvc.perform(builder).andExpect(contentMatcher).
                                 andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));


         player.setName("t");
         playerJson = asJsonString(player);
         builder = MockMvcRequestBuilders
                .post("/player/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

         contentMatcher = MockMvcResultMatchers.content()
                .string(PLAYER_NAME_3_LETTERS.getValue());
        mockMvc.perform(builder).andExpect(contentMatcher).
                andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

        player.setName("test");
        playerJson = asJsonString(player);
        builder = MockMvcRequestBuilders
                .post("/player/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(builder)
                            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value())).andReturn();

        String content = result.getResponse().getContentAsString();
        ResponseData data = (ResponseData) asObject(content, ResponseData.class);
        assertEquals(PLAYER_CREATED.getValue(), data.getMessage());

    }

}