package com.grapheople.opentable.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pax on 2018. 2. 7..
 */
@Data
@ConfigurationProperties(prefix = "google")
public class GoogleApiProperties {
    private String key;
    private final String kakaoPangyoLocation = "37.4016,127.1087";
    private final Integer defaultRadius = 500;
}
