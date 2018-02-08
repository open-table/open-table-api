package com.grapheople.opentable.controllers;

import com.grapheople.opentable.configs.properties.ElasticsearchProperties;
import com.grapheople.opentable.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pax on 2018. 2. 6..
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final ElasticsearchProperties elasticsearchProperties;
    private final RestaurantRepository restaurantRepository;

    @RequestMapping("/test")
    public String test(){
        System.out.println(restaurantRepository.findAll().get(0).getTelephone());
        return "test";
    }
}
