package rwth.elearning.ecognita.client.ecognitaclient.tasks;

/**
 * Created by ekaterina on 11.06.2015.
 */
public enum ApiPathEnum {
    ALL_COURSES("/api/courses/"),
    USER_COURSES("/api/account/enrolled"),
    USER_CHECK("/api/account/enrolled/"),
    USER_SIGN_UP("/api/users"),
    ENROLL_FOR_COURSE("/api/account/enrolled/"),
    LEAVE_COURSE("/api/account/enrolled/"),
    SUBSCRIBE_FOR_NOTIFICATIONS("/api/account/subscriptions/"),
    GET_LIST_OF_QUIZES("/api/quizzes/"),
    GET_QUIZ_QUESTIONS("/api/quizzes/"),
    SEE_SOLUTIONS("/api/account/solutions/"),
    SEE_SCORE("/api/account/scores/");

    private final String path;

    private ApiPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
