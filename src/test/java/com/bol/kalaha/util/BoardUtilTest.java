package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;


class BoardUtilTest {
    public static final Integer NUMBER_OF_STONES = 6;
    public static final Integer EMPTY_KALAHA = 0;
    @Test
    void initiateABoard(){

        Board board = BoardUtil.initiateABoard();
        List<Pit> actual = board.getPits();
        Assertions.assertThat(actual)
                .hasSize(14)
                .extracting(Pit::getPosition, Pit::getValue)
                .containsExactlyInAnyOrder(
                        tuple(1, NUMBER_OF_STONES),
                        tuple(2, NUMBER_OF_STONES),
                        tuple(3, NUMBER_OF_STONES),
                        tuple(4, NUMBER_OF_STONES),
                        tuple (5, NUMBER_OF_STONES),
                        tuple (6, NUMBER_OF_STONES),
                        tuple (7, EMPTY_KALAHA),
                        tuple (8, NUMBER_OF_STONES),
                        tuple (9, NUMBER_OF_STONES),
                        tuple (10, NUMBER_OF_STONES),
                        tuple (11, NUMBER_OF_STONES),
                        tuple(12, NUMBER_OF_STONES),
                        tuple(13, NUMBER_OF_STONES),
                        tuple (14, EMPTY_KALAHA)

                );
    }

}