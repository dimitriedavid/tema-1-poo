package models;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    private int favorites = 0;
    private int views = 0;

    public Show(final String title, final int year, final ArrayList<String> cast,
                final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public int getFavoritesCount() {
        return favorites;
    }

    public int getViewsCount() {
        return views;
    }

    // abstract functions
    /**
     * Adds a rating to a Movie / Serial
     * @param grade grade to be added
     * @param season if Serial, specifies season
     */
    public abstract void addRating(double grade, int season);

    public abstract double getShowRating();

    public abstract boolean isRated();

    public void addFavoriteCount() {
        favorites += 1;
    }

    public void addViewCount() {
        views += 1;
    }

    public void addViewCount(int count) {
        views += count;
    }

    public abstract int getDuration();
}
