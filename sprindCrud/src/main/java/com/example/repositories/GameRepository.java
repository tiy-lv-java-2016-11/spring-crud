package com.example.repositories;

import com.example.entities.Game;
import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer>{
    List<Game> findAll();
    List<Game> findByUser(User user);
    Game findById(int id);
    Game findByName(String name);
}
