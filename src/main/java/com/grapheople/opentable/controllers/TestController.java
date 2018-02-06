package com.grapheople.opentable.controllers;

import com.grapheople.opentable.properties.ElasticsearchProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pax on 2018. 2. 6..
 */
@RestController
public class TestController {
    @Autowired private final ElasticsearchProperties elasticsearchProperties;

    @RequestMapping("/test")
    public String test(){
        System.out.println(elasticsearchProperties.getUrl());
        return "test";
    }
}
