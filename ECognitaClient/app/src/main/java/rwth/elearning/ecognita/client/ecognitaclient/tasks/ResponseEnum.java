package rwth.elearning.ecognita.client.ecognitaclient.tasks;

/**
 * Created by ekaterina on 11.06.2015.
 */
public enum ResponseEnum {
    OK(200), CREATED(201), CONFLICT(409), BADREQUEST(400), UNAUTHORIZED(401);

    private final int code;

    private ResponseEnum(int code) {
        this.code = code;
    }

    public static ResponseEnum getResponseEnumByCode(int code) {
        for (ResponseEnum response : values()) {
            if (response.getCode() == code) {
                return response;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }
}
