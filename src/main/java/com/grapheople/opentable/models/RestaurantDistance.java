package com.grapheople.opentable.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "restaurant_distance")
public class RestaurantDistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name = "distance")
    private Float distance;

    public RestaurantDistance() {

    }

    public RestaurantDistance(Long restaurantId, String startingPoint, Float distance) {
        setRestaurantId(restaurantId);
        setStartingPoint(startingPoint);
        setDistance(distance);
    }
}
