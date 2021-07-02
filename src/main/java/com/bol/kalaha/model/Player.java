package com.bol.kalaha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table (name = "player")
@RequiredArgsConstructor
public class Player extends BasicModel {

	@NotNull
	@Size(min = 3, max = 50)
    @Getter @Setter
	private String name;

}
