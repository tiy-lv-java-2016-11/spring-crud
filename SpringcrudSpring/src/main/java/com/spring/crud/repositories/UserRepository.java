package com.spring.crud.repositories;

import com.spring.crud.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by darionmoore on 12/30/16.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
        User findFirstByName(String name);
}
