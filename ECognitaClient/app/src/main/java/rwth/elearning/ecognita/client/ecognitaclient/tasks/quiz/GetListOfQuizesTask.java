package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class GetListOfQuizesTask {
    private OnResponseListener onResponseListener;

    public void send(CourseListItem course) {
        new HttpGetListOfAllCoursesTask().execute(course);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetListOfAllCoursesTask extends AsyncTask<CourseListItem, Void, List<QuizListItem>> {

        @Override
        protected List<QuizListItem> doInBackground(CourseListItem... args) {
            HttpURLConnection conn = null;
            User user = LogInFragment.getConnectedUser();
            if (user == null) return null;
            try {
                CourseListItem course = args[0];
                String urlParameters =
                        "course_id=" + URLEncoder.encode(course.getId(), "UTF-8");
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.GET_LIST_OF_QUIZES.getPath() + "?" + urlParameters);

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
                        return JSONCourseQuizesParser.parse(sb.toString());
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
        protected void onPostExecute(List<QuizListItem> quizes) {
            super.onPostExecute(quizes);
            onResponseListener.onResponse(quizes);
        }
    }
}