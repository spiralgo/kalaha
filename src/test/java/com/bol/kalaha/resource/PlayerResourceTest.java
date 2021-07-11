package com.bol.kalaha.resource;

import com.bol.kalaha.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.bol.kalaha.util.JsonUtil.asJsonString;

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
        player.setName("test");
        String playerJson = asJsonString(player);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/player/create")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());


    }

}