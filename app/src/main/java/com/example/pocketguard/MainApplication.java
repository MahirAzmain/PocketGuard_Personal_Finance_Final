package com.example.pocketguard;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.File;
/**
 * The {@code MainApplication} class initializes application-level components and services.
 *
 * <p>This class uses the Singleton design pattern to ensure that a single instance of the
 * {@code MainApplication} class is created and provides a centralized initialization point
 * for various services like Firebase Cloud Messaging (FCM).</p>
 *
 * <p>Responsibilities of this class include:</p>
 * <ul>
 *   <li>Setting up notification channels for FCM on Android Oreo and above.</li>
 *   <li>Handling FCM token retrieval and logging.</li>
 *   <li>Configuring application directories and permissions.</li>
 * </ul>
 */
public class MainApplication extends Application {

    DatabaseReference databaseReference;
    public static final String FCM_CHANNEL_ID = "FCM_CHANNEL_ID";

    SharedPreferences sharedPreferences;
    String string_link, string_title, string_time;

    private static final String ONESIGNAL_APP_ID = "a0b06dcd-8db5-4e62-97bc-b810f23f822d";

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing Firebase
        FirebaseApp.initializeApp(this);

        // Set the code cache directory to read-only
        File dexOutputDir = getCodeCacheDir();
        dexOutputDir.setReadOnly();

        // For FCM Push -> Creating A New Channel ID
        // Create a new notification channel for FCM on Android Oreo and above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel fcmChannel = new NotificationChannel(FCM_CHANNEL_ID,"PCM_Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(fcmChannel);
        }

        // Retrieve and log the FCM registration token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "onComplete: Token Value :  failed", task.getException());
                    return;
                }
                // Get new FCM registration token
                String token = task.getResult();
                Log.d(TAG, "onComplete: Token Value : "+token);
            }
        });



    }
}
