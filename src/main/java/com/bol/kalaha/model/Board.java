package com.bol.kalaha.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "board")
public class Board extends BasicModel{

 	@OneToOne
	@JoinColumn (name = "game_id")
	@JsonBackReference
	@Getter @Setter
	private Game game;

	@OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@Getter @Setter
	private List<Pit> pits;

}
