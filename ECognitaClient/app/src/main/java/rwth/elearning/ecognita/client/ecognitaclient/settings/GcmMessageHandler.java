package rwth.elearning.ecognita.client.ecognitaclient.settings;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.SignInActivity;
import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 14.06.2015.
 */
public class GcmMessageHandler extends IntentService {
    public static final String TEXT_PROPERTY_NAME = "text";
    public static final String QUIZ_ID_PROPERTY_NAME = "quiz_id";
    private String issuedMessage;
    private String quizId;
    private Handler handler;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        issuedMessage = extras.getString(TEXT_PROPERTY_NAME);
        quizId = extras.getString(QUIZ_ID_PROPERTY_NAME);
        createNotification();
        Log.i("GCM", "Received : (" + messageType + ")  ");

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void createNotification() {
        User connectedUser = LogInFragment.getConnectedUser();
        Intent intent = new Intent(this, connectedUser == null ? SignInActivity.class : MyCoursesActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.small_notification_icon)
                        .setContentTitle("New quiz available!")
                        .setContentText(issuedMessage)
                        .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);
    }
}