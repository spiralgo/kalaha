package com.bol.kalaha.util;

import com.bol.kalaha.config.WebSocketActionEnum;
import com.bol.kalaha.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WebSocketUtil {
    public static String getMessageJSON(WebSocketActionEnum action, String message, Game game) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        rootNode.put("action", action.getValue());
        rootNode.put("message", message);

        if (game != null) {

            JsonNode gameNode = mapper.convertValue(game, JsonNode.class);
            rootNode.set("game", gameNode);
        } else {
            rootNode.put("game", "");
        }

        String result = "";
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
