package com.grapheople.opentable;

import com.grapheople.opentable.configs.properties.ElasticsearchProperties;
import com.grapheople.opentable.configs.properties.GoogleApiProperties;
import com.grapheople.opentable.configs.properties.NaverApiProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by pax on 2018. 2. 6..
 */
@SpringBootApplication
@EnableConfigurationProperties({ElasticsearchProperties.class, GoogleApiProperties.class, NaverApiProperties.class})
@EnableEncryptableProperties
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
