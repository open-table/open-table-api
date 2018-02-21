package com.grapheople.opentable.repositories;

import com.grapheople.opentable.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score getByUserIdAndRestaurantId(Integer userId, Long restaurantId);
}
