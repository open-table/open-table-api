package com.grapheople.opentable.repositories;

import com.grapheople.opentable.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
}
