package com.bol.kalaha.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table (name = "board")
public class Board {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name="id")
	private Long id;
	
	@OneToOne
	@JoinColumn (name = "game_id")
	@JsonBackReference
	private Game game;
	
	@OneToMany (mappedBy = "board")
	private List<Pit> pits;
	
	
	public Board() {
	}
	public Board (Game game) {
		this.game = game;
	}
	public Board(Long id, Game game, List<Pit> pits) {
		super();
		this.id = id;
		this.game = game;
		this.pits = pits;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

}
