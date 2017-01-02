package com.spring.crud.repositories;

import com.spring.crud.entities.Weapons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by darionmoore on 12/30/16.
 */
public interface CreateWeaponRepository extends JpaRepository<Weapons, Integer>{
        List<Weapons> findAllByOrderByDateTimeDesc();
}
