package action;

import jdk.jshell.spi.ExecutionControl;
import models.Action;
import models.Show;
import models.User;

import java.util.Map;

public class Command extends ActionCommon {
    public Command(Action action) {
        super(action);
    }

    @Override
    public String execute() {
        try {
            switch (action.getType()) {
                case "view":
                    return view();
                default:
                    throw new ExecutionControl.NotImplementedException("action not implemented");
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String view() {
        User user = database.getUserByUsername(action.getUsername());
        Show show = database.getShowByTitle(action.getTitle());

        Map<String, Integer> history = user.getHistory();

        Integer no_views = history.getOrDefault(show.getTitle(), 0);
        no_views += 1;
        history.put(show.getTitle(), no_views);

        return "success -> " + show.getTitle() + " was viewed with total views of " + no_views;
    }
}
