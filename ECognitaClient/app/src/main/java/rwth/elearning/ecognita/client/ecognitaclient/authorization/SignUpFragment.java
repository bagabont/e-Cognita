package rwth.elearning.ecognita.client.ecognitaclient.authorization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.user.SignUpUserTask;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class SignUpFragment extends Fragment {
    public static final String TAG = "sign_up_tag";
    private Button signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.signup_fragment, container, false);
        this.signupButton = (Button) fragmentView.findViewById(R.id.signup_button);
        this.signupButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText emailEditView = (EditText) fragmentView.findViewById(R.id.email_signup_input);
                        EditText matrNumberEditView = (EditText) fragmentView.findViewById(R.id.matr_number_input);
                        EditText firstNameEditView = (EditText) fragmentView.findViewById(R.id.first_name_input);
                        EditText lastNameEditView = (EditText) fragmentView.findViewById(R.id.last_name_input);
                        EditText passwordEditView = (EditText) fragmentView.findViewById(R.id.password_signup_input);

                        performSignUp(emailEditView.getText().toString(), matrNumberEditView.getText().toString(),
                                firstNameEditView.getText().toString(), lastNameEditView.getText().toString(),
                                passwordEditView.getText().toString());
                    }
                }
        );
        return fragmentView;
    }

    private void performSignUp(final String emailAddress, final String matrNumber, final String firstName, final String lastName, final String password) {
        SignUpUserTask signUpUserTask = new SignUpUserTask();
        signUpUserTask.setOnResponseListener(new OnResponseListener<ResponseEnum>() {

            @Override
            public void onResponse(ResponseEnum responseCode) {
                switch (responseCode) {
                    case CREATED:
                        SharedPreferences sharedpreferences = LogInFragment.getSharedpreferences();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(LogInFragment.email, emailAddress);
                        editor.putString(LogInFragment.password, password);
                        editor.putString(LogInFragment.firstName, firstName);
                        editor.putString(LogInFragment.lastName, lastName);
                        editor.putString(LogInFragment.matrNumber, matrNumber);
                        editor.commit();

                        Intent coursesActivityIntent = new Intent(getActivity(), MyCoursesActivity.class);
                        startActivity(coursesActivityIntent);
                        break;
                    default:
                        onError(getResources().getString(R.string.failed_to_signup_message));
                        break;
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
        signUpUserTask.send(new User.UserBuilder(emailAddress, password).firstName(firstName).lastName(lastName).build());
    }
}
