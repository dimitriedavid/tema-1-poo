package action;

import jdk.jshell.spi.ExecutionControl;
import main.Database;
import models.Action;

public abstract class ActionCommon {
    protected final Action action;
    protected final Database database;

    /**
     * This method executes the current
     * action specified in the {@link ActionCommon#action} field.
     * @return the result string
     */
    public abstract String execute() throws ExecutionControl.NotImplementedException;

    public ActionCommon(final Action action) {
        this.action = action;
        this.database = Database.getInstance();
    }
}
