package com.bol.kalaha.util;

//TODO: In the future, these messages should be taken from an i18n properties file.
public enum MessagesEnum {
    NONEMPTY("Player name should be non-empty."),
    GAME_OVER("The game is over."),
    NEED_OPPONENT("You need an opponent."),
    NOT_YOUR_TURN("It is not your turn."),
    YOUR_PITS_TOP("Your pits are on the top row."),
    YOUR_PITS_BOTTOM("Your pits are on the bottom row."),
    PIT_IS_EMPTY("This pit is empty.");

    private final String value;

    MessagesEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

}
