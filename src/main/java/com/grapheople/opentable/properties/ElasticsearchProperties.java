package com.grapheople.opentable.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pax on 2018. 2. 6..
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {
    private String url;
}
