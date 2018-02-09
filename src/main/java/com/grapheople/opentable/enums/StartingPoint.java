package com.grapheople.opentable.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

public enum StartingPoint {
    PANGYO("PANGYO", 321290, 533669);

    @Getter private final String name;
    @Getter private final Integer mapx;
    @Getter private final Integer mapy;

    StartingPoint(String name, Integer mapx, Integer mapy) {
        this.name = name;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    private static final Map<String, StartingPoint> m = Maps.newHashMap();

    static {
        for (StartingPoint p : StartingPoint.values())
            m.put(p.getName(), p);
    }

    public static StartingPoint convert(String value) {
        return m.get(value);
    }

    public static boolean isExist(String value) {
        return m.containsKey(value);
    }
}
