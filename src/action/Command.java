package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Show;
import models.User;

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
}
