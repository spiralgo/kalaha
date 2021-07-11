package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.repository.BoardRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.bol.kalaha.util.GameConstantsEnum.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepositoryRepository;

    private BoardService boardService;
    private Board board;

    @BeforeEach
    void setUp(){
        boardService = new BoardService(boardRepositoryRepository);
        prepareBoard();
    }
    void prepareBoard() {
        board = new Board();
        board.setId(1L);
        List<Pit> pits = new ArrayList<>();
        for (int i = 1; i <= KALAHA_PLAYER_TWO.getValue(); i++) {
            if (i == KALAHA_PLAYER_ONE.getValue() || i == KALAHA_PLAYER_TWO.getValue()) {
                Pit kalaha = new Pit();
                kalaha.setPosition(i);
                kalaha.setValue(EMPTY_KALAHA.getValue());
                pits.add(kalaha);
            } else {
                Pit pit = new Pit();
                pit.setPosition(i);
                pit.setValue(NUMBER_OF_STONES.getValue());
                pits.add(pit);

            }
        }
        board.setPits(pits);
    }
    @Test
    void updateBoard() {

        doReturn(board)
                .when(boardRepositoryRepository)
                .save(ArgumentMatchers.any(Board.class));

        assertThat(boardService.updateBoard(board).getId(),
                Matchers.equalTo(1L));

    }
}