package com.bol.kalaha.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table (name = "player")
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotNull
	@Size(min = 3, max = 50)
    private String name;
    

    public Player () {
    	
    }
    
    public Player(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Player(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    

    public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
        return id;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    

    
}
