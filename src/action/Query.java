package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Movie;
import models.Serial;
import models.Show;
import models.User;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Query extends ActionCommon {
    public Query(final Action action) {
        super(action);
    }

    @Override
    public String execute() throws ExecutionControl.NotImplementedException {
        String result;
        result = switch (action.getCriteria()) {
            case "average" -> null;
            case "awards" -> null;
            case "filter_description" -> null;
            case "ratings" -> ratings();
            case "favorite" -> favorite();
            case "longest" -> longest();
            case "most_viewed" -> mostViewed();
            case "num_ratings" -> numRatings();
            default -> throw new ExecutionControl.NotImplementedException("query criteria not"
                    + "implemented");
        };
        return "Query result: [" + result + "]";
    }

//    private class QueryActor {
//        public void applyFilter() {
//
//        }
//
//        public String generateResult() {
//            return null;
//        }
//    }

    private class QueryVideo {
        private ArrayList<Show> shows;

        QueryVideo() {
            shows = database.getShows();
        }

        public void applyFilter() {
            Stream<Show> stream = shows.stream();

            // object type
            stream = switch (action.getObjectType()) {
                case "movies" -> stream.filter(x -> x.getClass() == Movie.class);
                case "shows" -> stream.filter(x -> x.getClass() == Serial.class);
                default -> stream;
            };

            // year
            if (action.getFilters().get(0).get(0) != null) {
                // get year
                int yearFilter = Integer.parseInt(action.getFilters().get(0).get(0));
                // apply filter
                stream = stream.filter(x -> x.getYear() == yearFilter);
            }

            if (action.getFilters().get(1).get(0) != null) {
                // get genre
                String genreFilter = action.getFilters().get(1).get(0);
                // apply filter
                stream = stream.filter(x -> x.getGenres().contains(genreFilter));
            }

            // update shows
            shows = stream.collect(Collectors.toCollection(ArrayList::new));
        }

        public void customFilter(final Predicate<Show> customFilter) {
            Stream<Show> stream = shows.stream();
            stream = stream.filter(customFilter);
            shows = stream.collect(Collectors.toCollection(ArrayList::new));
        }

        public String generateResult() {
            // truncate shows at number elements
            int upperBound = Math.min(shows.size(), action.getNumber());
            ArrayList<Show> truncatedShows = new ArrayList<>(shows.subList(0, upperBound));

            // select only titles
            ArrayList<String> titles = truncatedShows.stream()
                                                     .map(Show::getTitle)
                                                     .collect(Collectors
                                                             .toCollection(ArrayList::new));
            return String.join(", ", titles);
        }

        public void sort(final Comparator<Show> comparator) {
            Comparator<Show> actualComparator = comparator;
            if (action.getSortType().equals("desc")) {
                actualComparator = comparator.reversed();
            }
            // apply sort
            this.shows = this.shows.stream()
                                   .sorted(actualComparator)
                                   .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private class QueryUser {
        private ArrayList<User> users;

        QueryUser() {
            this.users = database.getUsers();
        }

        public void applyFilter() {
            Stream<User> stream = users.stream();

            // select only users that have rated
            stream = stream.filter(x -> x.getRatingsCount() != 0);
            users = stream.collect(Collectors.toCollection(ArrayList::new));
        }

        public String generateResult() {
            // truncate shows at number elements
            int upperBound = Math.min(users.size(), action.getNumber());
            ArrayList<User> truncatedUsers = new ArrayList<>(users.subList(0, upperBound));

            // select only usernames
            ArrayList<String> usernames = truncatedUsers.stream()
                                                     .map(User::getUsername)
                                                     .collect(Collectors
                                                             .toCollection(ArrayList::new));
            return String.join(", ", usernames);
        }

        public void sort(final Comparator<User> comparator) {
            Comparator<User> actualComparator = comparator;
            if (action.getSortType().equals("desc")) {
                actualComparator = comparator.reversed();
            }
            // apply sort
            this.users = this.users.stream()
                                   .sorted(actualComparator)
                                   .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    // actual action execution
    private String ratings() {
        QueryVideo queryVideo = new QueryVideo();

        // apply common & custom filters
        queryVideo.applyFilter();
        queryVideo.customFilter(Show::isRated);

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getShowRating)
                                                .thenComparing(Show::getTitle);
        queryVideo.sort(comparator);

        return queryVideo.generateResult();
    }

    private String favorite() {
        QueryVideo queryVideo = new QueryVideo();

        // apply common filters
        queryVideo.applyFilter();
        queryVideo.customFilter((show) -> (show.getFavoritesCount() != 0));

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getFavoritesCount)
                                                .thenComparing(Show::getTitle);
        queryVideo.sort(comparator);

        return queryVideo.generateResult();
    }

    private String longest() {
        QueryVideo queryVideo = new QueryVideo();

        // apply common filters
        queryVideo.applyFilter();

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getDuration)
                                                .thenComparing(Show::getTitle);
        queryVideo.sort(comparator);

        return queryVideo.generateResult();
    }

    private String mostViewed() {
        QueryVideo queryVideo = new QueryVideo();

        // apply common filters
        queryVideo.applyFilter();
        queryVideo.customFilter((show) -> (show.getViewsCount() != 0));

        // custom sort
        Comparator<Show> comparator = Comparator.comparingDouble(Show::getViewsCount)
                                                .thenComparing(Show::getTitle);
        queryVideo.sort(comparator);

        return queryVideo.generateResult();
    }

    private String numRatings() {
        QueryUser queryUser = new QueryUser();

        // apply common filters
        queryUser.applyFilter();

        // custom sort
        Comparator<User> comparator = Comparator.comparingInt(User::getRatingsCount)
                                                .thenComparing(User::getUsername);
        queryUser.sort(comparator);

        return queryUser.generateResult();
    }
}
