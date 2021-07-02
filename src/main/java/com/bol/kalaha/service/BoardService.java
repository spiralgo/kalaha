package com.bol.kalaha.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepositoryMock) {
        this.boardRepository = boardRepositoryMock;
    }

    public Board createNewBoard(Board board) {
        boardRepository.save(board);
        return board;

    }

    public void updateBoard(Board board) {
        boardRepository.save(board);
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Optional<Board> getBoardByGame(Game game) {
        return boardRepository.findByGame(game);
    }

}
