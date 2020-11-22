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

public class ActionWorker {
    private class ActionResult {
        private final int id;
        private final String message;

        public ActionResult(int id, String message) {
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

    private Database database;

    public ActionWorker(Database database) {
        this.database = database;
    }

    public void executeAllActions(JSONArray arrayResult) {
        database.getActions().forEach(action -> {
            // exec action
            ActionResult result = externalExecute(action);

            // gen JSON Obj
            JSONObject object = new JSONObject();
            object.put(Constants.ID_STRING, result.getId());
            object.put(Constants.MESSAGE, result.getMessage());

            // add result to arrayResult
            arrayResult.add(object);
        });
    }

    // actual action parsing
    private ActionResult externalExecute(Action action) {
        // get action class based on action_type
        ActionCommon actionCommon;

        try {
            switch (action.getActionType()) {
                case "command":
                    actionCommon = new Command(action);
                    break;
                case "query":
                    actionCommon = new Query(action);
                    break;
                case "recommendation":
                    actionCommon = new Recommendation(action);
                    break;
                default:
                    throw new ExecutionControl.NotImplementedException("action type not implemented");
            }
        } catch (Exception e) {
            return null;
        }

        // execute external action based on type
        String result = actionCommon.execute();

        return new ActionResult(action.getActionId(), result);
    }
}
