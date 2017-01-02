package com.spring.crud.entities;

import javax.persistence.*;

/**
 * Created by darionmoore on 12/30/16.
 */
@Entity
@Table (name = "weapons")
public class Weapons {
    @Id
    @GeneratedValue
    private int id;

    @Column (nullable = false)
    private String description;


    @ManyToOne
    private User user;

    public Weapons(String descrption, User user) {
        this.description = descrption;
        this.user = user;
    }

    public Weapons() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrption) {
        this.description = descrption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
