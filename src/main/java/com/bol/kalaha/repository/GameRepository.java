package com.bol.kalaha.repository;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByPlayerOne(Player player);

    List<Game> findByPlayerTwo(Player player);


}
