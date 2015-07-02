package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

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
import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 02.07.2015.
 */
public class SeeSolutionsTask { private OnResponseListener onResponseListener;

    public void send(QuizListItem quizListItem) {
        new HttpGetListOfQuestionsTask().execute(quizListItem);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetListOfQuestionsTask extends AsyncTask<QuizListItem, Void, List<QuestionItem>> {

        @Override
        protected List<QuestionItem> doInBackground(QuizListItem... args) {
            HttpURLConnection conn = null;
            User user = LogInFragment.getConnectedUser();
            if (user == null) return null;
            try {
                QuizListItem quizListItem = args[0];
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.SEE_SOLUTIONS.getPath() + quizListItem.getId());

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
                        return JSONQuizSolutionsParser.parse(sb.toString());
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
        protected void onPostExecute(List<QuestionItem> quizes) {
            super.onPostExecute(quizes);
            onResponseListener.onResponse(quizes);
        }
    }
}