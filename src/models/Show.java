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

    /**
     * This method returns the number of times this show was added on a user's favorites
     * list
     * @return favorite count of a show
     */
    public int getFavoritesCount() {
        return favorites;
    }

    /**
     * This method returns the view count of a show
     * @return view count of a show
     */
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

    /**
     * This method calculates the average rating for a show
     * @return the calculated rating
     */
    public abstract double getShowRating();

    /**
     * This method checks if a show was rated
     * @return boolean that represents if a show was rated or not
     */
    public abstract boolean isRated();

    /**
     * This method increases the number of times a user added the show to favorites list
     */
    public void addFavoriteCount() {
        favorites += 1;
    }

    /**
     * This method increases the number of times this show was seen
     */
    public void addViewCount() {
        views += 1;
    }

    /**
     * This method increases the number of times this show was seen
     * @param count increases the view counter by this
     */
    public void addViewCount(final int count) {
        views += count;
    }

    /**
     * This method returns the duration of the movie. In case of serials, it returns
     * the sum of the duration of all the seasons.
     * @return duration of a show
     */
    public abstract int getDuration();
}
