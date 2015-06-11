package rwth.elearning.ecognita.client.ecognitaclient.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class User implements Serializable {
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String matrNumber;

    private User(UserBuilder builder) {
        this.emailAddress = builder.nestedEmailAddress;
        this.password = builder.nestedPassword;
        this.firstName = builder.nestedFirstName;
        this.lastName = builder.nestedLastName;
        this.matrNumber = builder.nestedMatrNumber;
    }

    /**
     * Returns the user's email address
     *
     * @return the user's email address
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMatrNumber() {
        return this.matrNumber;
    }

    /**
     * Returns the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * A builder pattern class through which the user's data is being built.
     */
    public static class UserBuilder {
        private String nestedEmailAddress;
        private String nestedPassword;
        private String nestedFirstName;
        private String nestedLastName;
        private String nestedMatrNumber;

        public UserBuilder(String emailAddress, String nestedPassword) {
            this.nestedEmailAddress = emailAddress;
            this.nestedPassword = nestedPassword;
        }

        public UserBuilder(User user) {
            this.nestedEmailAddress = user.emailAddress;
            this.nestedPassword = user.password;
            this.nestedFirstName = user.firstName;
            this.nestedLastName = user.lastName;
            this.nestedMatrNumber = user.matrNumber;
        }

        /**
         * Sets the first name of the logging in user
         *
         * @param firstName the first name of the logging in user
         * @return the current instance of the builder
         */
        public UserBuilder firstName(String firstName) {
            this.nestedFirstName = firstName;
            return this;
        }

        /**
         * Sets the last name of the logging in user
         *
         * @param lastName the last name of the logging in user
         * @return the current instance of the builder
         */
        public UserBuilder lastName(String lastName) {
            this.nestedLastName = lastName;
            return this;
        }

        public UserBuilder matrNumber(String matrNumber) {
            this.nestedMatrNumber = matrNumber;
            return this;
        }

        /**
         * Builds the instance of the user
         *
         * @return the built user entity
         */
        public User build() {
            return new User(this);
        }
    }
}