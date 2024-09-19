package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

public class Movie extends Production {

    // Constructor with title, runtime, and description
    public Movie(String title, int runtime, String description, LocalDate releaseDate) {
        super(title, runtime, description);
        this.setUtgivelsesdato(releaseDate);
    }

    // Constructor with title, runtime, and image URL
    public Movie(String title, int runtime, String imageUrl) {
        super(title, runtime, imageUrl);
    }

    // Default constructor
    public Movie() {
    }

    // toString method
    @Override
    @JsonIgnore
    public String toString() {
        return "Title: " + super.getTitle() + " Release Date: " + super.getUtgivelsesdato();
    }
}
