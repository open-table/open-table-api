package com.grapheople.opentable.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

public enum  DistanceType {
    ALL("all", 0),
    NEAREST("nearest", 100),
    NEAR("near", 200),
    MORE("more", 600),
    FAR("far", 1000);

    @Getter
    private final String type;
    @Getter private final Integer distance;

    DistanceType(String type, Integer distance) {
        this.type = type;
        this.distance = distance;
    }

    private static final Map<String, DistanceType> m = Maps.newHashMap();

    static {
        for (DistanceType p : DistanceType.values())
            m.put(p.getType(), p);
    }

    public static DistanceType convert(String type) {
        return m.get(type);
    }

    public static boolean isExist(String value) {
        return m.containsKey(value);
    }
}
