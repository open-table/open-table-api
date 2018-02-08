package com.grapheople.opentable.repositories;

import com.grapheople.opentable.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pax on 2018. 2. 8..
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
