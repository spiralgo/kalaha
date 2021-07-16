package com.bol.kalaha.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "game")
@RequiredArgsConstructor
public class Game extends BasicModel {


    @ManyToOne
    @JoinColumn(name = "player_one_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Player playerOne;

    @ManyToOne
    @JoinColumn(name = "player_two_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Player playerTwo;

    @ManyToOne
    @JoinColumn(name = "turn_of", referencedColumnName = "id")
    @Getter
    @Setter
    private Player turnOf;

    @Getter
    @Setter
    @JsonProperty(value = "isOver")
    private boolean isOver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Board board;

}
