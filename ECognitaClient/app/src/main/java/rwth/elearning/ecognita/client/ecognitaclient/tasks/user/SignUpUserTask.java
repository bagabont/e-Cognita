package rwth.elearning.ecognita.client.ecognitaclient.tasks.user;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 11.06.2015.
 */
public class SignUpUserTask {
    private OnResponseListener onResponseListener;

    public void send(User user) {
        new HttpSignUpUserTask().execute(user);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpSignUpUserTask extends AsyncTask<User, Void, ResponseEnum> {

        @Override
        protected ResponseEnum doInBackground(User... args) {
            HttpURLConnection conn = null;
            try {
                User user = args[0];
                String urlParameters =
                        "email=" + URLEncoder.encode(user.getEmailAddress(), "UTF-8") +
                                "&password=" + URLEncoder.encode(user.getPassword(), "UTF-8") +
                                "&firstname=" + URLEncoder.encode(user.getFirstName(), "UTF-8") +
                                "&lastname=" + URLEncoder.encode(user.getLastName(), "UTF-8");

                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.USER_SIGN_UP.getPath());

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
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
