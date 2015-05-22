package rwth.elearning.ecognita.client.ecognitaclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class MyCoursesActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        User loggedInUser = (User) extras.getSerializable(LogInFragment.USER_TAG);

        setContentView(R.layout.activity_my_courses);
        TextView userLogin = (TextView) findViewById(R.id.user_login_text);
        userLogin.setText(loggedInUser.getEmailAddress());
    }
}
