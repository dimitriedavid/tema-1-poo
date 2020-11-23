package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Movie;
import models.Serial;
import models.Show;

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
        String result = null;
        result = switch (action.getCriteria()) {
            case "average" -> null;
            case "awards" -> null;
            case "filter_description" -> null;
            case "ratings" -> ratings();
            case "favorite" -> favorite();
            case "longest" -> longest();
            case "most_viewed" -> most_viewed();
            case "num_ratings" -> null;
            default -> throw new ExecutionControl.NotImplementedException("query criteria not"
                    + "implemented");
        };
        return "Query result: [" + result + "]";
    }

    private interface QueryCommon {
        void applyFilter();

        String generateResult();
    }

    private class QueryActor implements QueryCommon {
        @Override
        public void applyFilter() {

        }

        @Override
        public String generateResult() {
            return null;
        }
    }

    private class QueryVideo implements QueryCommon {
        private ArrayList<Show> shows;

        QueryVideo() {
            shows = database.getShows();
        }

        @Override
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
                Integer yearFilter = Integer.parseInt(action.getFilters().get(0).get(0));
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

        @Override
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

        public ArrayList<Show> getShows() {
            return shows;
        }

        public void setShows(final ArrayList<Show> shows) {
            this.shows = shows;
        }

        public void sort(Comparator<Show> comparator) {
            if (action.getSortType().equals("desc")) {
                comparator = comparator.reversed();
            }
            // apply sort
            ArrayList<Show> sortedShows = this.getShows().stream()
                                              .sorted(comparator)
                                              .collect(Collectors.toCollection(ArrayList::new));
            this.setShows(sortedShows);
        }
    }

    private class QueryUser implements QueryCommon {
        @Override
        public void applyFilter() {

        }

        @Override
        public String generateResult() {
            return null;
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

    private String most_viewed() {
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
}
