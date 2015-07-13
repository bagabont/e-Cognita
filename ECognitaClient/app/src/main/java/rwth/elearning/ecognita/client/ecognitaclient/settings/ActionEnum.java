package rwth.elearning.ecognita.client.ecognitaclient.settings;

/**
 * Created by ekaterina on 13.07.2015.
 */
public enum ActionEnum {
    PUBLISHED("publish"),
    CLOSED("close");

    private final String actionName;

    private ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return this.actionName;
    }
}