package rwth.elearning.ecognita.client.ecognitaclient.authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesActivity;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.user.CheckUserTask;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class LogInFragment extends Fragment {
    public static final String HOST_ADDRESS = "http://e-cognita.herokuapp.com";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String email = "emailKey";
    public static final String password = "passwordKey";
    public static final String firstName = "firstNameKey";
    public static final String lastName = "lastNameKey";
    public static final String matrNumber = "matrNumberKey";
    private static final String TAG = "plug_login_tag";
    private static SharedPreferences sharedpreferences;


    public static final String USER_TAG = "user";
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.login_fragment, container, false);
        this.loginButton = (Button) fragmentView.findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptToLogin(fragmentView.findViewById(R.id.email_input), fragmentView.findViewById(R.id.password_input));
                    }
                }
        );
        return fragmentView;
    }

    private void attemptToLogin(View emailInput, View passwordInput) {
        if (emailInput instanceof EditText && passwordInput instanceof EditText) {
            String emailAddress = ((EditText) emailInput).getText().toString();
            String passwordValue = ((EditText) passwordInput).getText().toString();

            performLogin(emailAddress, passwordValue);
        }
    }

    private void performLogin(String emailAddress, String passwordValue) {
        CheckUserTask checkUserTask = new CheckUserTask();
        checkUserTask.setOnResponseListener(new OnResponseListener<User>() {

            @Override
            public void onResponse(User connectedUser) {
                if (connectedUser != null) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(email, connectedUser.getEmailAddress());
                    editor.putString(password, connectedUser.getPassword());
                    editor.putString(firstName, connectedUser.getFirstName());
                    editor.putString(lastName, connectedUser.getLastName());
                    editor.putString(matrNumber, connectedUser.getMatrNumber());
                    editor.commit();

                    Intent coursesActivityIntent = new Intent(getActivity(), MyCoursesActivity.class);
                    startActivity(coursesActivityIntent);
                } else {
                    onError(getResources().getString(R.string.failed_to_login_message));
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        checkUserTask.send(new User.UserBuilder(emailAddress, passwordValue).build());
    }

    /**
     * Retrieves the connected user from the preferences file, where the logged in information is stored,
     * e.g. his user name, password.
     *
     * @return a connected user or null if no user is connected
     */
    public static User getConnectedUser() {
        if (sharedpreferences != null && sharedpreferences.contains(email)) {
            String pass = sharedpreferences.getString(password, "");
            String emailAddress = sharedpreferences.getString(email, "");
            return new User.UserBuilder(emailAddress, pass).build();
        }
        return null;
    }

    /**
     * Logges out the user from the system by removing his session information
     * from the preference file. The user then is redirected to the initial log in page.
     *
     * @param currentActivity the activity from which action bar the user picked a log out option.
     */
    public static void performLogout(Activity currentActivity) {
        if (sharedpreferences != null) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();

            //redirect to the login screen
            Intent redirectToLoginIntent = new Intent(currentActivity, SignInActivity.class);
            currentActivity.startActivity(redirectToLoginIntent);
            currentActivity.finish();
        }
    }

    /**
     * Encodes the user's logging name and password as a base64 string to be sent to the server.
     *
     * @param login a user's logging name
     * @param pass  a user's password
     * @return the encoded string, containing user's logging name and password as a base64 string
     */
    public static String getB64Auth(String login, String pass) {
        String source = login + ":" + pass;
        String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }

    public static SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }
}
