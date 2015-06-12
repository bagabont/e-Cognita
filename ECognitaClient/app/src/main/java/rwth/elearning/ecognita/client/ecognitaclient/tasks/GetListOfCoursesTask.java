package rwth.elearning.ecognita.client.ecognitaclient.tasks;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 12.06.2015.
 */
public class GetListOfCoursesTask {
    private OnResponseListener onResponseListener;
    private final ApiPathEnum apiPathEnum;

    public GetListOfCoursesTask(ApiPathEnum apiPathEnum) {
        this.apiPathEnum = apiPathEnum;
    }

    public void send(User user) {
        new HttpGetListOfAllCoursesTask().execute(user);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetListOfAllCoursesTask extends AsyncTask<User, Void, List<CourseListItem>> {

        @Override
        protected List<CourseListItem> doInBackground(User... args) {
            HttpURLConnection conn = null;
            try {
                User user = args[0];
                URL url = new URL(LogInFragment.HOST_ADDRESS + apiPathEnum.getPath());

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(user.getEmailAddress(), user.getPassword()));
                conn.setDoInput(true);
                conn.connect();
                int code = conn.getResponseCode();
                ResponseEnum responseCode = ResponseEnum.getResponseEnumByCode(code);
                switch (responseCode) {
                    case OK:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return JSONUserCoursesParser.parse(sb.toString());
                }
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                onResponseListener.onError(e.getMessage());
            } catch (JSONException e) {
                onResponseListener.onError(e.getMessage());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<CourseListItem> courses) {
            super.onPostExecute(courses);
            onResponseListener.onResponse(courses);
        }
    }
}