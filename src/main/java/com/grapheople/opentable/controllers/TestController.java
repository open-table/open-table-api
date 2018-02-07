package com.grapheople.opentable.controllers;

import com.grapheople.opentable.configs.properties.ElasticsearchProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pax on 2018. 2. 6..
 */
@RestController
@EnableConfigurationProperties({ElasticsearchProperties.class})
@RequiredArgsConstructor
public class TestController {
    private final ElasticsearchProperties elasticsearchProperties;

    @RequestMapping("/test")
    public String test(){
        System.out.println(elasticsearchProperties.getUrl());
        return "test";
    }
}
