package models;

import java.util.ArrayList;

public final class Movie extends Show {
    private final int duration;
    private final ArrayList<Double> ratings;

    public Movie(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void addRating(final double grade, final int season) {
        ratings.add(grade);
    }
}
