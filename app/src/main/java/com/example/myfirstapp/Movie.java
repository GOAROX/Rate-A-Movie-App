package com.example.myfirstapp;

public class Movie {
    private String movieName;
    private String dateWatched;
    private float rating;
    private boolean direction;
    private boolean story;
    private boolean animation;
    private boolean acting;
    private boolean storybuilding;
    private boolean yearn;

    public Movie(String movieName, String dateWatched, float rating,
                 boolean direction, boolean story, boolean animation,
                 boolean acting, boolean storybuilding, boolean yearn) {
        this.movieName = movieName;
        this.dateWatched = dateWatched;
        this.rating = rating;
        this.direction = direction;
        this.story = story;
        this.animation = animation;
        this.acting = acting;
        this.storybuilding = storybuilding;
        this.yearn = yearn;
    }

    // Getters for movie properties
    public String getMovieName() {
        return movieName;
    }

    public String getDateWatched() {
        return dateWatched;
    }

    public float getRating() {
        return rating;
    }

    public boolean isDirection() {
        return direction;
    }

    public boolean isStory() {
        return story;
    }

    public boolean isAnimation() {
        return animation;
    }

    public boolean isActing() {
        return acting;
    }

    public boolean isStorybuilding() {
        return storybuilding;
    }

    public boolean isYearn() {
        return yearn;
    }
}

