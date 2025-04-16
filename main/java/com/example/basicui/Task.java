package com.example.basicui;

import androidx.annotation.NonNull;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean completed;

    // Default constructor
    public Task() {
    }
    // Constructor with parameters
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getTitle() {
//        return title;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getDescription() {
//        return description;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return title + (completed ? " (Completed)" : " (Pending)");
    }
}