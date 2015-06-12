package rwth.elearning.ecognita.client.ecognitaclient.tasks;

/**
 * Created by ekaterina on 11.06.2015.
 */
public enum ApiPathEnum {
    ALL_COURSES("/api/courses/"),
    USER_COURSES("/api/account/courses/enrolled"),
    USER_CHECK("/api/account/courses/enrolled"),
    USER_SIGN_UP("/api/users"),
    ENROLL_FOR_COURSE("/api/account/courses/enrolled"),
    LEAVE_COURSE("/api/account/courses/enrolled/");

    private final String path;

    private ApiPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}