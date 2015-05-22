package rwth.elearning.ecognita.client.ecognitaclient;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;


public class SignInActivity extends FragmentActivity {
    private static final String LOGIN_TAB_TAG = "login";
    private static final String SIGNUP_TAB_TAG = "signup";

    private FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(LOGIN_TAB_TAG).setIndicator(getString(R.string.login_tab_label)),
                LogInFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(SIGNUP_TAB_TAG).setIndicator(getString(R.string.signup_tab_label)),
                SignUpFragment.class, null);
    }
}
