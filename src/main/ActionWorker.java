package main;

import action.ActionCommon;
import action.Command;
import action.Query;
import action.Recommendation;
import common.Constants;
import jdk.jshell.spi.ExecutionControl;
import models.Action;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class ActionWorker {
    private static class ActionResult {
        private final int id;
        private final String message;

        ActionResult(final int id, final String message) {
            this.id = id;
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }
    }

    private final Database database;

    public ActionWorker(final Database database) {
        this.database = database;
    }

    /**
     * This method iterates through all the actions in the {@link ActionWorker#database}
     * and executes each of them, updating <code>arrayResult</code> with the result of
     * the action.
     *
     * @param arrayResult JSONArray that is updated with each action executed
     */
    public void executeAllActions(final JSONArray arrayResult) {
        database.getActions().forEach(action -> {
            // exec action
            ActionResult result = null;
            try {
                result = externalExecute(action);
            } catch (ExecutionControl.NotImplementedException e) {
                e.printStackTrace();
            }

            // gen JSON Obj
            JSONObject object = new JSONObject();
            object.put(Constants.ID_STRING, result.getId());
            object.put(Constants.MESSAGE, result.getMessage());

            // add result to arrayResult
            arrayResult.add(object);
        });
    }

    // actual action parsing
    private ActionResult externalExecute(final Action action) throws
            ExecutionControl.NotImplementedException {
        // get action class based on action_type
        ActionCommon actionCommon;

        actionCommon = switch (action.getActionType()) {
            case "command" -> new Command(action);
            case "query" -> new Query(action);
            case "recommendation" -> new Recommendation(action);
            default -> throw new ExecutionControl.NotImplementedException("action type not "
                    + "implemented");
        };

        // execute external action based on type
        String result = actionCommon.execute();

        return new ActionResult(action.getActionId(), result);
    }
}
