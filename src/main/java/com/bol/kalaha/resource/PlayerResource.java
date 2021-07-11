package com.bol.kalaha.resource;

import com.bol.kalaha.exception.ResponseData;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bol.kalaha.util.MessagesEnum.PLAYER_CREATED;

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
    public ResponseEntity<ResponseData> createNewPlayer(@Valid @RequestBody Player player) {
        Player savedPlayer = playerService.createPlayer(player);
        ResponseData<Player> data = new ResponseData<>(PLAYER_CREATED.getValue(), savedPlayer);
        return ResponseEntity.ok(data);
    }
}
