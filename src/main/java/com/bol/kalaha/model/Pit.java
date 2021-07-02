package com.bol.kalaha.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.RequiredArgsConstructor;

@Entity
@Table (name="pit")
@RequiredArgsConstructor
public class Pit extends BasicModel{

	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "board_id")
	@JsonBackReference
	private Board board;

	@NotNull
	@Column (name="pit_number_in_game")
	private Integer position;

	@NotNull
	@Column (name = "value")
	private Integer value;


	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
