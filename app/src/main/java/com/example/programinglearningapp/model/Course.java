package com.example.programinglearningapp.model;

public class Course {
    private int id;
    private String title;
    private String description;
    private String imageUrl;

    public Course(String title, String imageUrl, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
