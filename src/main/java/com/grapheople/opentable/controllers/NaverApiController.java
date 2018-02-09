package com.grapheople.opentable.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.grapheople.opentable.models.Restaurant;
import com.grapheople.opentable.services.NaverMapServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return naverMapServices.findRestaurant(query, page, pageSize, sort);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/restaurants")
    public String insertLocalInfo(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize) {
        String query = "판교 음식집";
        String sort = "random";
        naverMapServices.insertRestaurant(query, page, pageSize, sort);
        return "test";
    }
}
