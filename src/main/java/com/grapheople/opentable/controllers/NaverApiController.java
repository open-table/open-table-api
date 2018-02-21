package com.grapheople.opentable.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.grapheople.opentable.enums.StartingPoint;
import com.grapheople.opentable.models.Restaurant;
import com.grapheople.opentable.services.NaverMapService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

/**
 * Created by pax on 2018. 2. 7..
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/naver/")
public class NaverApiController {
    private final NaverMapService naverMapService;

    @ApiIgnore
    @RequestMapping(method = RequestMethod.GET, value = "/restaurants/db")
    public ResponseEntity<List<Restaurant>> getLocalInfoFromDB() {
        return new ResponseEntity<>(naverMapService.getRestaurantListFromDB(), HttpStatus.OK);
    }

    @ApiIgnore
    @RequestMapping(method = RequestMethod.GET, value = "/restaurants/naver")
    public ResponseEntity<JsonNode> getLocalInfoFromNaver(@RequestParam(value = "query", defaultValue = "판교 음식집") String query,
                                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                                          @RequestParam(value = "sort", defaultValue = "random") String sort) {

        return new ResponseEntity<>(naverMapService.getRestaurantLustFromNaver(query, page, pageSize, sort), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/restaurants")
    public ResponseEntity<String> insertLocalInfo(@RequestParam(value = "query", defaultValue = "판교 음식집") String query,
                                                  @RequestParam(value = "sort", defaultValue = "random") String sort,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                                  @RequestParam(value = "startingPoint", required = false) String startingPoint) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("restaurant save done", HttpStatus.OK);
        if (!Strings.isNullOrEmpty(startingPoint) && !StartingPoint.isExist(startingPoint)) {
            responseEntity = new ResponseEntity<>("unknown statingPoint", HttpStatus.OK);
        } else {
            naverMapService.insertRestaurant(query, page, pageSize, sort, startingPoint);
        }
        return responseEntity;
    }

    @ApiOperation(value = "식당 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(value = "startingPoint", defaultValue = "PANGYO", required = false) String startingPoint,
                                                           @RequestParam(value = "distanceType", defaultValue = "all", required = false) String distanceType,
                                                           @RequestParam(value = "category", required = false) String category,
                                                           @RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "100", required = false) Integer pageSize) {
        return new ResponseEntity<>(naverMapService.getRestaurantList(startingPoint, distanceType, category, title, page, pageSize), HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 리스트")
    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public ResponseEntity<Set<String>> getCategories() {
        return new ResponseEntity<>(naverMapService.getCategories(), HttpStatus.OK);
    }
}
