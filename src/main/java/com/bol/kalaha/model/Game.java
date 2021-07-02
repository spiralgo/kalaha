package com.bol.kalaha.model;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Table (name="game")
@RequiredArgsConstructor
public class Game extends BasicModel {

	
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

	
	
	public Game(Player playerOne, Player playerTwo, Player turnOfWithId, boolean isOver) {
		super();

		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.turnOfWithId = turnOfWithId;
		this.isOver = isOver;
	}

	public Player getPlayerOne () {
		return playerOne;
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
