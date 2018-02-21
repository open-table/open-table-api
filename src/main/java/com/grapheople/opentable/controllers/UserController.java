package com.grapheople.opentable.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.grapheople.opentable.models.User;
import com.grapheople.opentable.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/score")
    public ResponseEntity<Boolean> insertScore(@RequestParam(name = "restaurant_id") Long restaurantId,
                                             @RequestParam(name = "score") Integer score) {
        User user = userService.getUserById(1);
        return new ResponseEntity<>(userService.insertScore(user.getId(), restaurantId, score), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/score")
    public ResponseEntity<Boolean> updateScore(@RequestParam(name = "restaurant_id") Long restaurantId,
                                             @RequestParam(name = "score") Integer score) {
        User user = userService.getUserById(1);
        return new ResponseEntity<>(userService.updateScore(user.getId(), restaurantId, score), HttpStatus.OK);
    }


}
