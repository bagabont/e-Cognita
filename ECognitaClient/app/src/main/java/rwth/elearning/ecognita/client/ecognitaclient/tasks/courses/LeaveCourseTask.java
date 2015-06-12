package rwth.elearning.ecognita.client.ecognitaclient.tasks.courses;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ResponseEnum;

/**
 * Created by ekaterina on 12.06.2015.
 */
public class LeaveCourseTask {
    private CourseListItem item;
    private OnResponseListener onResponseListener;

    public LeaveCourseTask(CourseListItem item) {
        this.item = item;
    }

    public void send() {
        new HttpLeaveCourseTask().execute(item);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpLeaveCourseTask extends AsyncTask<CourseListItem, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CourseListItem... params) {
            HttpURLConnection conn = null;
            User connectedUser = LogInFragment.getConnectedUser();
            if (connectedUser == null) return false;

            CourseListItem courseItem = params[0];
            try {

                URL url = new URL(LogInFragment.HOST_ADDRESS + ApiPathEnum.LEAVE_COURSE.getPath() + courseItem.getId());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(connectedUser.getEmailAddress(), connectedUser.getPassword()));
                conn.setDoInput(true);
                conn.connect();
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
