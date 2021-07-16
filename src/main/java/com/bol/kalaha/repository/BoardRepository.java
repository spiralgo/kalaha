package com.bol.kalaha.repository;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
