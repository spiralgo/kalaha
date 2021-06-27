package com.bol.kalaha.repository;

import com.bol.kalaha.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository <Player, Long> {

	Player findByName(String name);
    
}
