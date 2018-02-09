package com.grapheople.opentable.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.grapheople.opentable.enums.StartingPoint;
import com.grapheople.opentable.services.NaverMapServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pax on 2018. 2. 7..
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/naver/")
public class NaverApiController {
    private final NaverMapServices naverMapServices;

    @RequestMapping(method = RequestMethod.GET, value = "/restaurants")
    public JsonNode getLocalInfo(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                 @RequestParam(value = "sort", defaultValue = "random") String sort) {
        String query = "판교 음식집";
        return naverMapServices.getRestaurantLustFromNaver(query, page, pageSize, sort);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/restaurants")
    public ResponseEntity<String> insertLocalInfo(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                  @RequestParam(value = "startingPoint", required = false) String startingPoint) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("restaurant save done", HttpStatus.OK);
        String query = "판교 음식집";
        String sort = "random";
        if (!Strings.isNullOrEmpty(startingPoint) && !StartingPoint.isExist(startingPoint)) {
            responseEntity = new ResponseEntity<>("unknown statingPoint", HttpStatus.OK);
        }
        naverMapServices.insertRestaurant(query, page, pageSize, sort, startingPoint);
        return responseEntity;
    }
}
