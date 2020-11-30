package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Show;
import models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Recommendation extends ActionCommon {
    public Recommendation(final Action action) {
        super(action);
    }

    @Override
    public String execute() throws ExecutionControl.NotImplementedException {
        return switch (action.getType()) {
            case "standard" -> standard();
            case "best_unseen" -> bestUnseen();
            case "favorite" -> favorite();
            case "popular" -> popular();
            case "search" -> search();
            default -> throw new ExecutionControl.NotImplementedException("action not"
                    + "implemented");
        };
    }

    private static final class Helper {

        public static Show returnFistUnseen(final User user, final ArrayList<Show> shows) {
            Map<String, Integer> userHistory = user.getHistory();
            for (Show show : shows) {
                if (!userHistory.containsKey(show.getTitle())) {
                    return show;
                }
            }
            return null;
        }

        public static String resultSingle(final Show show) {
            if (show == null) {
                return "";
            } else {
                return show.getTitle();
            }
        }

        public static ArrayList<Show> sortShows(final ArrayList<Show> shows,
                                          final Comparator<Show> comparator, final String order) {
            Comparator<Show> actualComparator = comparator;
            if (order.equals("desc")) {
                actualComparator = comparator.reversed();
            }
            // apply sort
            return shows.stream()
                        .sorted(actualComparator)
                        .collect(Collectors.toCollection(ArrayList::new));
        }

        public static ArrayList<Show> customFilterShows(final ArrayList<Show> shows,
                                                  final Predicate<Show> customFilter) {
            Stream<Show> stream = shows.stream();
            stream = stream.filter(customFilter);
            return stream.collect(Collectors.toCollection(ArrayList::new));
        }

        public static String generateResult(final ArrayList<Show> shows) {
            // select only titles
            ArrayList<String> titles = shows.stream()
                                            .map(Show::getTitle)
                                            .collect(Collectors
                                                    .toCollection(ArrayList::new));
            return String.join(", ", titles);
        }
    }

    private String standard() {
        User user = database.getUserByUsername(action.getUsername());

        Show firstUnseen = Helper.returnFistUnseen(user, database.getShows());

        if (firstUnseen == null) {
            return "StandardRecommendation cannot be applied!";
        }

        return "StandardRecommendation result: " + Helper.resultSingle(firstUnseen);
    }

    private String bestUnseen() {
        User user = database.getUserByUsername(action.getUsername());
        ArrayList<Show> shows = database.getShows();

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getShowRating);
        shows = Helper.sortShows(shows, comparator, "desc");

        Show firstUnseen = Helper.returnFistUnseen(user, shows);

        if (firstUnseen == null) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }

        return "BestRatedUnseenRecommendation result: " + Helper.resultSingle(firstUnseen);
    }

    private String popular() {
        User user = database.getUserByUsername(action.getUsername());
        ArrayList<String> genresByPopularity = database.getGenresByPopularity();

        // assert premium user
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            return "PopularRecommendation cannot be applied!";
        }

        for (String genre : genresByPopularity) {
            ArrayList<Show> showsInGenre = database.getShowsByGenre(genre);
            Show firstUnseen = Helper.returnFistUnseen(user, showsInGenre);
            if (firstUnseen != null) {
                return "PopularRecommendation result: " + Helper.resultSingle(firstUnseen);
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    private String favorite() {
        User user = database.getUserByUsername(action.getUsername());
        ArrayList<Show> shows = database.getShows();

        // assert premium user
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            return "FavoriteRecommendation cannot be applied!";
        }

        // filter for shows that appear on at least one user's favorites list
        shows = Helper.customFilterShows(shows, (show) -> (show.getFavoritesCount() != 0));

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getFavoritesCount);
        shows = Helper.sortShows(shows, comparator, "desc");

        Show firstUnseen = Helper.returnFistUnseen(user, shows);

        if (firstUnseen == null) {
            return "FavoriteRecommendation cannot be applied!";
        }

        return "FavoriteRecommendation result: " + Helper.resultSingle(firstUnseen);
    }

    private String search() {
        User user = database.getUserByUsername(action.getUsername());

        // assert premium user
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            return "SearchRecommendation cannot be applied!";
        }

        String genre = action.getGenre();
        ArrayList<Show> showsInGenre = database.getShowsByGenre(genre);

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getShowRating)
                                                .thenComparing(Show::getTitle);
        showsInGenre = Helper.sortShows(showsInGenre, comparator, "asc");

        // remove seen videos
        Map<String, Integer> userHistory = user.getHistory();
        showsInGenre.removeIf(show -> userHistory.containsKey(show.getTitle()));

        if (showsInGenre.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }

        return "SearchRecommendation result: [" + Helper.generateResult(showsInGenre) + "]";
    }
}
