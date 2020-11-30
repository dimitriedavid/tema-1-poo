package entertainment;

/**
 * The types of movies/series that will be used in the tests
 * <p>
 * DO NOT MODIFY
 */
public enum Genre {
    TV_MOVIE, DRAMA, FANTASY, COMEDY, FAMILY, WAR, SCI_FI_FANTASY, CRIME,
    ANIMATION, SCIENCE_FICTION, ACTION, HORROR, MYSTERY, WESTERN, ADVENTURE,
    ACTION_ADVENTURE, ROMANCE, THRILLER, KIDS, HISTORY;


    @Override
    public String toString() {
        return switch (this) {
            case ACTION -> "Action";
            case ADVENTURE -> "Adventure";
            case DRAMA -> "Drama";
            case COMEDY -> "Comedy";
            case CRIME -> "Crime";
            case ROMANCE -> "Romance";
            case WAR -> "War";
            case HISTORY -> "History";
            case THRILLER -> "Thriller";
            case MYSTERY -> "Mystery";
            case FAMILY -> "Family";
            case HORROR -> "Horror";
            case FANTASY -> "Fantasy";
            case SCIENCE_FICTION -> "Science Fiction";
            case ACTION_ADVENTURE -> "Action & Adventure";
            case SCI_FI_FANTASY -> "Sci-Fi & Fantasy";
            case ANIMATION -> "Animation";
            case KIDS -> "Kids";
            case WESTERN -> "Western";
            case TV_MOVIE -> "Tv Movie";
        };
    }
}
