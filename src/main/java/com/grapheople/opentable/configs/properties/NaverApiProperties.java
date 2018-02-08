package com.grapheople.opentable.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pax on 2018. 2. 8..
 */
@Data
@ConfigurationProperties(prefix = "naver")
public class NaverApiProperties {
    private String id;
    private String secret;
}
