package com.grapheople.opentable.services;

import com.grapheople.opentable.models.Score;
import com.grapheople.opentable.models.User;
import com.grapheople.opentable.repositories.ScoreRepository;
import com.grapheople.opentable.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public Boolean insertScore(Integer userId, Long restaurantId, Integer score) {
        Score scoreInstance = new Score(userId, restaurantId, score);
        return scoreRepository.save(scoreInstance) != null;
    }

    public Boolean updateScore(Integer userId, Long restaurantId, Integer score) {
        Score scoreInstance = scoreRepository.getByUserIdAndRestaurantId(userId, restaurantId);
        scoreInstance.setScore(score);
        return  scoreRepository.save(scoreInstance) != null;
    }
}
