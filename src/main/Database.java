package main;

import fileio.Input;
import models.*;

import java.util.ArrayList;

public class Database {
    private ArrayList<Actor> actors = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Show> shows = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();

    public Database() {
    }

    // populate
    public void populate(Input input) {
        // actors
        input.getActors().forEach(actor -> actors.add(
            new Actor(
                actor.getName(),
                actor.getCareerDescription(),
                actor.getFilmography(),
                actor.getAwards()
            )
        ));

        // users
        input.getUsers().forEach(user -> users.add(
            new User(
                user.getUsername(),
                user.getSubscriptionType(),
                user.getHistory(),
                user.getFavoriteMovies()
            )
        ));

        // movies
        input.getMovies().forEach(movie -> shows.add(
            new Movie(
                movie.getTitle(),
                movie.getYear(),
                movie.getCast(),
                movie.getGenres(),
                movie.getDuration()
            )
        ));

        // serials
        input.getSerials().forEach(serial -> shows.add(
            new Serial(
                serial.getTitle(),
                serial.getYear(),
                serial.getCast(),
                serial.getGenres(),
                serial.getNumberSeason(),
                serial.getSeasons()
            )
        ));

        // actions
        input.getCommands().forEach(cmd -> actions.add(
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
}
