package com.bol.kalaha.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table (name="game")
public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne
		@JoinColumn (
				name="player_one_id",

				referencedColumnName = "id")		
	private Player playerOne;
	@ManyToOne
		@JoinColumn (
				name="player_two_id",
				referencedColumnName = "id")

	private Player playerTwo;
	
	@ManyToOne
	@JoinColumn (
			name="turn_of_with_id",
			referencedColumnName = "id")	
	
	private Player turnOfWithId;

	private boolean isOver;
	
	public Game(Player playerOne, Player playerTwo) {
		super();
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		turnOfWithId = playerOne;
		isOver = false;
		
	}

	
	
	public Game(Long id, Player playerOne, Player playerTwo, Player turnOfWithId, boolean isOver) {
		super();
		this.id = id;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.turnOfWithId = turnOfWithId;
		this.isOver = isOver;
	}



	public Game() {
		super();
	}
	public Player getPlayerOne () {
		return playerOne;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Player getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}
	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}
	public Player getTurnOfWithId() {
		return turnOfWithId;
	}
	public void setTurnOfWithId(Player turnOfWithId) {
		this.turnOfWithId = turnOfWithId;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	
	
	
}
