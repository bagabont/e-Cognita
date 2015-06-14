package rwth.elearning.ecognita.client.ecognitaclient.tasks;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 14.06.2015.
 */
public class SubscribeForNotificationsTask {
    private OnResponseListener onResponseListener;
    private String regid;

    public void send(User user, String regid) {
        new HttpSubscribeForNotificationsTask().execute(user);
        this.regid = regid;
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpSubscribeForNotificationsTask extends AsyncTask<User, Void, ResponseEnum> {

        @Override
        protected ResponseEnum doInBackground(User... args) {
            HttpURLConnection conn = null;
            try {
                User user = args[0];
                String urlParameters =
                        "token=" + URLEncoder.encode("" + regid, "UTF-8");

                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.SUBSCRIBE_FOR_NOTIFICATIONS.getPath() + regid);

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(user.getEmailAddress(), user.getPassword()));
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                conn.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                conn.setRequestProperty("Content-Language", "en-US");

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(
                        conn.getOutputStream());
                dataOutputStream.writeBytes(urlParameters);
                dataOutputStream.flush();
                dataOutputStream.close();

                int status = conn.getResponseCode();
                return ResponseEnum.getResponseEnumByCode(status);
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                onResponseListener.onError(e.getMessage());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseEnum responseCode) {
            super.onPostExecute(responseCode);
            onResponseListener.onResponse(responseCode);
        }
    }
}