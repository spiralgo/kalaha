package com.bol.kalaha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;

public interface BoardRepository extends JpaRepository <Board, Long> {
	
	public Optional<Board> findByGame(Game game);

}
