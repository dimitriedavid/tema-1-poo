package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Add grade to season
     * @param grade to be added
     */
    public void addRating(final double grade) {
        ratings.add(grade);
    }

    public double getSeasonRating() {
        if (ratings.size() == 0) {
            return 0;
        }
        return ratings.stream().mapToDouble(Double::doubleValue).sum() / ratings.size();
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }

    public boolean isRated() {
        return ratings.size() != 0;
    }
}

