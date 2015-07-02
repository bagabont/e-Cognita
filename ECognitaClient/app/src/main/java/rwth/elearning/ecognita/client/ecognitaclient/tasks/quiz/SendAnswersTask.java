package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.model.UserAnswer;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 30.06.2015.
 */
public class SendAnswersTask {
    private OnResponseListener onResponseListener;
    private final String quizId;

    public SendAnswersTask(String quizId) {
        this.quizId = quizId;
    }

    public void send(List<UserAnswer> answers) {
        new HttpSendAnswersTask().execute(answers);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpSendAnswersTask extends AsyncTask<List<UserAnswer>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<UserAnswer>... params) {
            HttpURLConnection conn = null;
            User connectedUser = LogInFragment.getConnectedUser();
            if (connectedUser == null) return false;

            List<UserAnswer> userAnswers = params[0];
            try {
                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.GET_LIST_OF_QUIZES.getPath() + quizId + "/solutions");
                JSONArray answersJson = new JSONArray();
                for (UserAnswer userAnswer : userAnswers) {
                    JSONObject answer = new JSONObject();
                    answer.put("question_id", userAnswer.getQuestionId());
                    answer.put("selected", userAnswer.getChoice());
                    answersJson.put(answer);
                }

                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(connectedUser.getEmailAddress(), connectedUser.getPassword()));
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Language", "en-US");

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(answersJson.toString());
                wr.flush();
                wr.close();

                int status = conn.getResponseCode();
                return status == ResponseEnum.NOCONTENT.getCode();
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
            return false;
        }

        @Override
        protected void onPostExecute(Boolean statusChangedSuccessfully) {
            super.onPostExecute(statusChangedSuccessfully);
            onResponseListener.onResponse(statusChangedSuccessfully);
        }
    }
}
