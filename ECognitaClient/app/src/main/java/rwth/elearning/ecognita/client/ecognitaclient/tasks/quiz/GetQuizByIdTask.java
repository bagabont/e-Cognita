package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 12.07.2015.
 */
public class GetQuizByIdTask {
    private OnResponseListener onResponseListener;

    public void send(String quizId) {
        new HttpGetListOfAllCoursesTask().execute(quizId);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetListOfAllCoursesTask extends AsyncTask<String, Void, QuizListItem> {

        @Override
        protected QuizListItem doInBackground(String... args) {
            HttpURLConnection conn = null;
            User user = LogInFragment.getConnectedUser();
            if (user == null) return null;
            try {
                String quizId = args[0];
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.GET_QUIZ_BY_ID.getPath() + quizId);

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
                        return JSONCourseQuizesParser.parseItem(sb.toString());
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
        protected void onPostExecute(QuizListItem quiz) {
            super.onPostExecute(quiz);
            onResponseListener.onResponse(quiz);
        }
    }
}
