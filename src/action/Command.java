package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Show;
import models.User;

import java.util.ArrayList;
import java.util.Map;

public final class Command extends ActionCommon {
    public Command(final Action action) {
        super(action);
    }

    @Override
    public String execute() {
        try {
            return switch (action.getType()) {
                case "view" -> view();
                case "favorite" -> favorite();
                case "rating" -> rating();
                default -> throw new ExecutionControl.NotImplementedException("action not"
                        + "implemented");
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String view() {
        User user = database.getUserByUsername(action.getUsername());
        Show show = database.getShowByTitle(action.getTitle());

        Map<String, Integer> history = user.getHistory();

        Integer noViews = history.getOrDefault(show.getTitle(), 0);
        noViews += 1;
        history.put(show.getTitle(), noViews);

        return "success -> " + show.getTitle() + " was viewed with total views of " + noViews;
    }

    private String favorite() {
        User user = database.getUserByUsername(action.getUsername());
        Show show = database.getShowByTitle(action.getTitle());

        ArrayList<String> favoriteMovies = user.getFavoriteMovies();

        // check if show was not viewed
        if (!user.isShowViewed(show.getTitle())) {
            return "error -> " + show.getTitle() + " is not seen";
        }

        // duplicate check
        if (favoriteMovies.contains(show.getTitle())) {
            return "error -> " + show.getTitle() + " is already in favourite list";
        }

        // add show to fav
        favoriteMovies.add(show.getTitle());
        return "success -> " + show.getTitle() + " was added as favourite";
    }

    private String rating() {
        User user = database.getUserByUsername(action.getUsername());
        Show show = database.getShowByTitle(action.getTitle());

        Map<String, Double> userRatingsHistory = user.getRatingsHistory();

        // check if show was not viewed
        if (!user.isShowViewed(show.getTitle())) {
            return "error -> " + show.getTitle() + " is not seen";
        }

        // duplicate check
        if (userRatingsHistory.containsKey(show.getTitle())) {
            return "error -> " + show.getTitle() + " is already rated";
        }

        // add rating to show
        show.addRating(action.getGrade(), action.getSeasonNumber());
        return "success -> " + show.getTitle() + " was rated with " + action.getGrade() + " by "
                + user.getUsername();
    }
}
