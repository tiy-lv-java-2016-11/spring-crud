package com.example.repositories;

import com.example.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer>{
    List<Game> findAll();
}
