package models;

import entertainment.Season;

import java.util.ArrayList;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(String title, int year, ArrayList<String> cast, ArrayList<String> genres, final int numberOfSeasons, final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
