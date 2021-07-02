package com.bol.kalaha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.repository.PitRepository;

@Service
@Transactional
public class PitService {

	@Autowired
	private PitRepository pitRepository;

	public PitService(PitRepository pitRepositoryMock) {
		this.pitRepository = pitRepositoryMock;
	}

	public Pit updatePitNumberOfStones(Board board, Integer position, Integer newValue, boolean isToSum) {
		Pit toMergePit = pitRepository.findByBoardAndPosition(board, position);

		if (isToSum) {
			toMergePit.setValue(toMergePit.getValue() + newValue);
		} else {
			toMergePit.setValue(newValue);
		}

		pitRepository.save(toMergePit);
		return toMergePit;
	}


	public Pit updatePitIncrement(Board board, Integer position) {
		Pit toMergePit = pitRepository.findByBoardAndPosition(board, position);

		int pitValue = toMergePit.getValue();
		pitValue++;
		toMergePit.setValue(pitValue);

		pitRepository.save(toMergePit);
		return toMergePit;
	}

	public Pit updatePitStealPit(Board board, Integer positionReceiving, Integer positionGiving) {
		Pit toMergePitReceiving = pitRepository.findByBoardAndPosition(board, positionReceiving);
		Pit toMergePitGiving = pitRepository.findByBoardAndPosition(board, positionGiving);
		Pit newPit = toMergePitReceiving;
		
		newPit = updatePitNumberOfStones(board, positionReceiving, toMergePitGiving.getValue(), true);
		updatePitNumberOfStones(board, positionGiving, 0, false);

		return newPit;
	}

	public int getPitNumberOfStonesByBoardAndPosition(Board board, int i) {
		int value = pitRepository.findByBoardAndPosition(board, i).getValue();
		return value;
	}

}
