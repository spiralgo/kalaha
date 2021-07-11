package com.bol.kalaha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "player")
@RequiredArgsConstructor
public class Player extends BasicModel {

    @NotNull(message = "Please provide a player name.")
    @Size(min = 3, max = 50, message = "Player name should have at least 3 letters.")
    @Getter
    @Setter
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Player))
            return false;

        Player other = (Player) o;

        return getId() != null &&
                getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
