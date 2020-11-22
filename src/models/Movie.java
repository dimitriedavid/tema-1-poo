package models;

import java.util.ArrayList;

public final class Movie extends Show {
    private final int duration;

    public Movie(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
