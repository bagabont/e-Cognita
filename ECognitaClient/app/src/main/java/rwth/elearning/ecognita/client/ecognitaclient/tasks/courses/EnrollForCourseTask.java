package rwth.elearning.ecognita.client.ecognitaclient.tasks.courses;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 12.06.2015.
 */
public class EnrollForCourseTask {
    private CourseListItem item;
    private OnResponseListener onResponseListener;

    public EnrollForCourseTask(CourseListItem item) {
        this.item = item;
    }

    public void send() {
        new HttpGetChangeStateTask().execute(item);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetChangeStateTask extends AsyncTask<CourseListItem, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CourseListItem... params) {
            HttpURLConnection conn = null;
            User connectedUser = LogInFragment.getConnectedUser();
            if (connectedUser == null) return false;

            CourseListItem courseItem = params[0];
            try {
                String urlParameters =
                        "course_id=" + URLEncoder.encode(courseItem.getId(), "UTF-8");

                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.ENROLL_FOR_COURSE.getPath() + courseItem.getId());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(connectedUser.getEmailAddress(), connectedUser.getPassword()));
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
                ResponseEnum statusCode = ResponseEnum.getResponseEnumByCode(status);
                switch (statusCode) {
                    case NOCONTENT:
                        return true;
                }
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
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
