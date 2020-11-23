package main;

import fileio.Input;
import models.Action;
import models.Actor;
import models.Show;
import models.User;
import models.Movie;
import models.Serial;

import java.util.ArrayList;
import java.util.Map;

public final class Database {
    private final ArrayList<Actor> actors = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Show> shows = new ArrayList<>();
    private final ArrayList<Action> actions = new ArrayList<>();

    // singleton
    private static Database instance = null;

    private Database() {
    }

    /**
     * @return new clean database
     */
    public static Database getNewInstance() {
        instance = new Database();
        return instance;
    }

    /**
     * @return current database
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Parses <code>input</code> to populate the {@link Database}
     * class.
     *
     * @param input from json
     */
    public void populate(final Input input) {
        // actors
        input.getActors()
             .forEach(actor -> actors.add(
                     new Actor(
                             actor.getName(),
                             actor.getCareerDescription(),
                             actor.getFilmography(),
                             actor.getAwards()
                     )
             ));

        // movies
        input.getMovies()
             .forEach(movie -> shows.add(
                     new Movie(
                             movie.getTitle(),
                             movie.getYear(),
                             movie.getCast(),
                             movie.getGenres(),
                             movie.getDuration()
                     )
             ));

        // serials
        input.getSerials()
             .forEach(serial -> shows.add(
                     new Serial(
                             serial.getTitle(),
                             serial.getYear(),
                             serial.getCast(),
                             serial.getGenres(),
                             serial.getNumberSeason(),
                             serial.getSeasons()
                     )
             ));

        // users
        // placed under shows to parse favorites
        input.getUsers()
             .forEach(user -> {
                 users.add(
                         new User(
                                 user.getUsername(),
                                 user.getSubscriptionType(),
                                 user.getHistory(),
                                 user.getFavoriteMovies()
                         )
                 );
                 // parse favorites to update each show
                 parseUserFavorites(user.getFavoriteMovies());
                 parseUserViews(user.getHistory());
             });

        // actions
        input.getCommands()
             .forEach(cmd -> actions.add(
                     new Action(
                             cmd.getActionId(),
                             cmd.getActionType(),
                             cmd.getType(),
                             cmd.getUsername(),
                             cmd.getObjectType(),
                             cmd.getSortType(),
                             cmd.getCriteria(),
                             cmd.getTitle(),
                             cmd.getGenre(),
                             cmd.getNumber(),
                             cmd.getGrade(),
                             cmd.getSeasonNumber(),
                             cmd.getFilters()
                     )
             ));
    }

    private void parseUserFavorites(final ArrayList<String> favoriteShows) {
        for (String showTitle : favoriteShows) {
            Show show = getShowByTitle(showTitle);
            show.addFavoriteCount();
        }
    }

    private void parseUserViews(final Map<String, Integer> history) {
        for (Map.Entry<String, Integer> historyEntry : history.entrySet()) {
            Show show = getShowByTitle(historyEntry.getKey());
            show.addViewCount(historyEntry.getValue());
        }
    }


    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * Search username in database and return it.
     *
     * @param username to be searched
     * @return found user or null
     */
    public User getUserByUsername(final String username) {
        return users.stream()
                    .filter(x -> username.equals(x.getUsername()))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Search show in database and return it.
     *
     * @param title to be searched
     * @return found show or null
     */
    public Show getShowByTitle(final String title) {
        return shows.stream()
                    .filter(x -> title.equals(x.getTitle()))
                    .findFirst()
                    .orElse(null);
    }
}
