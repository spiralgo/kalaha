package com.bol.kalaha.controller;

import com.bol.kalaha.model.Friend;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @GetMapping
    public List<Friend> list() {
        return Arrays.asList(new Friend("Erdem"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Friend create(@RequestParam final String name) {
        return new Friend(name);
    }
}