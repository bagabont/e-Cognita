package rwth.elearning.ecognita.client.ecognitaclient.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class User implements Serializable {
    private String emailAddress;
    private String password;

    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getPassword() {
        return this.password;
    }
}
