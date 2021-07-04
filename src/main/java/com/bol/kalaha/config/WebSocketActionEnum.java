package com.bol.kalaha.config;

public enum WebSocketActionEnum {

    REFRESH_GAME_LIST("refresh_game_list"),
    REFRESH_BOARD("refresh_board"),
    END("end");
    private final String value;

    WebSocketActionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
