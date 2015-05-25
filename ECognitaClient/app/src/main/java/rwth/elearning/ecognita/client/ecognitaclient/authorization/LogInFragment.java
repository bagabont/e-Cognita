package rwth.elearning.ecognita.client.ecognitaclient.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesActivity;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class LogInFragment extends Fragment {
    public static final String USER_TAG = "user";
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

            User loggedInUser = getLoggedInUser(emailAddress, passwordValue);
            Intent coursesActivityIntent = new Intent(getActivity(), MyCoursesActivity.class);
            coursesActivityIntent.putExtra(USER_TAG, loggedInUser);
            startActivity(coursesActivityIntent);
        }
    }

    private User getLoggedInUser(String emailAddress, String passwordValue) {
        //TODO: change to connection and verification with the server
        return new User(emailAddress, passwordValue);
    }
}
