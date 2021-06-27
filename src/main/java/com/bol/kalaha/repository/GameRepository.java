package com.bol.kalaha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;

public interface GameRepository extends JpaRepository <Game, Long> {

	List<Game> findByPlayerOne(Player player);

	List<Game> findByPlayerTwo(Player player);
	

}
