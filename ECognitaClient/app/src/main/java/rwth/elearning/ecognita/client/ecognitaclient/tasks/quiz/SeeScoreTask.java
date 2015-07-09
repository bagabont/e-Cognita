package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by ekaterina on 09.07.2015.
 */
public class SeeScoreTask {
    private static final String SCORE_PROPERTY_NAME = "score";
    private OnResponseListener onResponseListener;

    public void send(QuizListItem quizListItem) {
        new HttpGetListOfQuestionsTask().execute(quizListItem);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetListOfQuestionsTask extends AsyncTask<QuizListItem, Void, String> {

        @Override
        protected String doInBackground(QuizListItem... args) {
            HttpURLConnection conn = null;
            User user = LogInFragment.getConnectedUser();
            if (user == null) return null;
            try {
                QuizListItem quizListItem = args[0];
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.SEE_SCORE.getPath() + quizListItem.getId());

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
                        JSONObject scoreObject = new JSONObject(sb.toString());
                        return scoreObject.getString(SCORE_PROPERTY_NAME);
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
        protected void onPostExecute(String score) {
            super.onPostExecute(score);
            onResponseListener.onResponse(score);
        }
    }
}
