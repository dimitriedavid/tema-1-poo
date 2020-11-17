package models;

import java.util.ArrayList;

public class Movie extends Show {
    private final int duration;

    public Movie(String title, int year, ArrayList<String> cast, ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
