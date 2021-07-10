package com.bol.kalaha.service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepositoryMock) {
        this.boardRepository = boardRepositoryMock;
    }

    public void updateBoard(Board board) {
        boardRepository.save(board);
    }

}
