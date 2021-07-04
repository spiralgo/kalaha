package com.bol.kalaha.resource;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/player")
public class PlayerResource {

    @Autowired
    private PlayerService playerService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> createNewPlayer(@Valid @RequestBody Player player, HttpServletResponse response) {
        Player savedPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }
}
