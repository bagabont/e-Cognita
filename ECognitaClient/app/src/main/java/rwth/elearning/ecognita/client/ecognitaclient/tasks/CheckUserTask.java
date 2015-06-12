package rwth.elearning.ecognita.client.ecognitaclient.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 11.06.2015.
 */
public class CheckUserTask {
    private OnResponseListener onResponseListener;

    /**
     * Sends a GET request to authenticate a user
     *
     * @param user a user's entered data
     */
    public void send(User user) {
        new HttpCheckUserTask().execute(user);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpCheckUserTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... args) {
            User loggingInUser = args[0];

            HttpURLConnection conn = null;
            try {
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.USER_CHECK.getPath());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(loggingInUser.getEmailAddress(), loggingInUser.getPassword()));
                conn.setDoInput(true);
                conn.connect();
                int code = conn.getResponseCode();
                ResponseEnum responseCode = ResponseEnum.getResponseEnumByCode(code);
                switch (responseCode) {
                    case OK:
                    case CREATED:
                        //TODO parse JSON and build the user with the builder (adding extracted first and last names
                        return loggingInUser;
                }
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (ProtocolException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(User connectedUser) {
            super.onPostExecute(connectedUser);
            onResponseListener.onResponse(connectedUser);
        }
    }
}
