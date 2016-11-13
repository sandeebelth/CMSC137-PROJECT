package game.components;

import java.io.Serializable;

public class Action implements Serializable {
    private String fromName;
    private int key;
    private String affectedName;
    private ActionType actionType;
    private int value1;
    private int value2;

    public Action(String fromName, int key, String affectedName, ActionType actionType, int value1, int value2) {
        this.fromName = fromName;
        this.key = key;
        this.affectedName = affectedName;
        this.actionType = actionType;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getFromName() {
       return fromName;
    }

    public int getKey() {
        return key;
    }

    public String getAffectedName() {
        return affectedName;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}
