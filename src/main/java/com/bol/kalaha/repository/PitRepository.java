package com.bol.kalaha.repository;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PitRepository extends JpaRepository<Pit, Long> {

    Pit findByBoardAndPosition(Board board, Integer position);

}
