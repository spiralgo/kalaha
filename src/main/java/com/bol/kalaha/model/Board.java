package com.bol.kalaha.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board")
public class Board extends BasicModel {


    @OneToOne(mappedBy = "board")
    @JsonBackReference
    @Getter
    @Setter
    private Game game;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Getter
    @Setter
    private List<Pit> pits;

}
