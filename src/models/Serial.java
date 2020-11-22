package models;

import entertainment.Season;

import java.util.ArrayList;

public final class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(final String title, final int year, final ArrayList<String> cast,
                  final ArrayList<String> genres, final int numberOfSeasons,
                  final ArrayList<Season> seasons) {
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
