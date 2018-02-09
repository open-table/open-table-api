package com.grapheople.opentable.repositories;

import com.grapheople.opentable.models.RestaurantDistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDistanceRepository extends JpaRepository<RestaurantDistance, Long> {
}
