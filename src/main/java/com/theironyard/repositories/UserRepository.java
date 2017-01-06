package com.theironyard.repositories;

import com.theironyard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by melmo on 12/29/16.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findFirstByUsername(String username);
}
