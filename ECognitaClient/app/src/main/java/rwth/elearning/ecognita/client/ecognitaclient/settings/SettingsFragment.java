package rwth.elearning.ecognita.client.ecognitaclient.settings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.SubscribeForNotificationsTask;

import java.io.IOException;

/**
 * Created by ekaterina on 14.06.2015.
 */
public class SettingsFragment extends Fragment {
    private GoogleCloudMessaging gcm;
    private String regid;
    private String PROJECT_NUMBER = "AIzaSyB1ywrBADZYLGXtRLQ8bxvyL0JpSHv03ls";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        Button registerButton = (Button) view.findViewById(R.id.subscribe_for_notifications_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        Button unregisterButton = (Button) view.findViewById(R.id.unsubscribe_for_notifications_button);
        unregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregister();
            }
        });
        return view;
    }

    private void register() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getActivity().getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);
                    postRequest();

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getActivity(), msg + "\n", Toast.LENGTH_SHORT).show();
            }
        }.execute(null, null, null);
    }

    private void postRequest() {
        SubscribeForNotificationsTask subscribeForNotificationsTask = new SubscribeForNotificationsTask();
        subscribeForNotificationsTask.setOnResponseListener(new OnResponseListener<ResponseEnum>() {

            @Override
            public void onResponse(ResponseEnum responseCode) {
                switch (responseCode) {
                    case NOCONTENT:
                        //successgfully subscribed
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        onError(getResources().getString(R.string.failed_to_subscribe_message));
                        break;
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("tag", errorMessage);
            }
        });
        subscribeForNotificationsTask.send(LogInFragment.getConnectedUser(), regid);
    }

    private void unregister() {

    }
}
