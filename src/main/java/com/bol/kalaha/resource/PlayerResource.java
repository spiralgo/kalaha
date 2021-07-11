package com.bol.kalaha.resource;

import com.bol.kalaha.exception.ResourceException;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/player")
public class PlayerResource {

    private final PlayerService playerService;

    @Autowired
    public PlayerResource(PlayerService playerService) {
        this.playerService = playerService;
    }
    @PostMapping(value = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> createNewPlayer(@Valid @RequestBody Player player) {
        Player savedPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }
}
