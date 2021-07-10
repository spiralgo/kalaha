package com.bol.kalaha.util;

public enum GameConstantsEnum {


    NUMBER_OF_STONES(6),
    EMPTY_KALAHA(0),
    PIT_0_PLAYER_ONE(1),
    PIT_0_PLAYER_TWO(8),
    KALAHA_PLAYER_ONE(7),
    KALAHA_PLAYER_TWO(14);

    private final Integer value;

    GameConstantsEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
