package com.grapheople.opentable.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "scores")
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Score() {

    }

    public Score(Integer userId, Long restaurantId, Integer score) {
        setUserId(userId);
        setRestaurantId(restaurantId);
        setScore(score);
        setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }
}
