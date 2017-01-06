package com.theironyard.models;

import javax.persistence.*;

/**
 * Created by melmo on 12/29/16.
 */
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String coverUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private int year;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    private User user;

    public Book() {
    }

    public Book(String coverUrl, String title, String author, String year, String description, String status, User user) {
        this.coverUrl = coverUrl;
        this.title = title;
        this.author = author;
        this.year = Integer.parseInt(year);
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
