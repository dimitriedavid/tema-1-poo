package models;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;
    private double averageShowRating = 0;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAverageShowRating(final double averageShowRating) {
        this.averageShowRating = averageShowRating;
    }

    public double getAverageShowRating() {
        return averageShowRating;
    }

    public int getAwardsCount() {
        return awards.values().stream().mapToInt(Integer::intValue).sum();
    }
}
