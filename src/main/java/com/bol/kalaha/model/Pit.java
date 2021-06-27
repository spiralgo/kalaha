package com.bol.kalaha.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table (name="pit")
public class Pit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonBackReference
	private Board board;

	@Column (name="pit_number_in_game")
	private Integer position;
	
	@Column (name = "value")
	private Integer value;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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


	public Pit(Board board, Integer position, Integer value) {
		super();
		this.board = board;
		this.position = position;
		this.value = value;
	}

	public Pit() {
		super();
	}

}
