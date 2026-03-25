package com.example.pocketguard;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
/**
 * This class represents the application class for the PocketGuard app.
 * It initializes the application-wide settings and resources, including notification channels for FCM (Firebase Cloud Messaging).
 *
 * Design Patterns Used:
 * 1. Singleton Pattern: The Application class acts as a singleton instance for the entire application lifecycle.
 * 2. Factory Method Pattern: Used indirectly in the creation of NotificationChannel. NotificationChannel constructor acts as a factory method for creating notification channels.
 * 3. Template Method Pattern: The onCreate() method serves as a template method for initializing the application. Subclasses may override this method to provide custom initialization logic.
 * 4. Dependency Injection Pattern: The getSystemService() method is used for obtaining system services, demonstrating dependency injection.
 *
 */
public class App extends Application {
    //Constants
    public static final String FCM_CHANNEL_ID = "PUSH_NOTICE";
    /**
     * Called when the application is starting.
     * Initializes application-wide settings and resources.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Set read-only permission for the code cache directory
        File dexOutputDir = getCodeCacheDir();
        dexOutputDir.setReadOnly();

        // Create notification channel for FCM (Firebase Cloud Messaging) if API level is Oreo or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel fcmChannel = new NotificationChannel(FCM_CHANNEL_ID,"PCM_Channel", NotificationManager.IMPORTANCE_HIGH);
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
