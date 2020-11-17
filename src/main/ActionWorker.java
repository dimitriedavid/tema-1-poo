package main;

import common.Constants;
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
            ActionResult result = execute(action);

            // gen JSON Obj
            JSONObject object = new JSONObject();
            object.put(Constants.ID_STRING, result.getId());
            object.put(Constants.MESSAGE, result.getMessage());

            // add result to arrayResult
            arrayResult.add(object);
        });
    }

    private ActionResult execute(Action action) {
        return new ActionResult(1, "test");
    }
}
