package com.example.pocketguard;

import static android.content.Context.MODE_PRIVATE;
import static com.example.pocketguard.App.FCM_CHANNEL_ID;
import static com.example.pocketguard.Key_Static.NOTIFICATION_ID;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.example.pocketguard.Account.Income_Expense_Balance_Update;
import com.example.pocketguard.Database.Sqlite_DatabaseSource;
import com.example.pocketguard.GoalSet.GoalRecyclerView;
import com.example.pocketguard.Map.Location_Make;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * The {@code Key_Static_Methods} class provides a collection of static utility methods for handling
 * various operations related to shared preferences, Firebase synchronization, and notifications.
 *
 * <p>This class uses multiple design patterns:</p>
 * <ul>
 *   <li><strong>Singleton Pattern:</strong> Ensures that shared preferences are accessed through a single instance.</li>
 *   <li><strong>Observer Pattern:</strong> Implements Firebase database listeners to sync data changes in real-time.</li>
 * </ul>
 *
 * <p>These methods help in centralizing and organizing common tasks such as saving data, updating Firebase,
 * creating notifications, and managing dialogs.</p>
 *
 * @see android.content.SharedPreferences
 * @see com.google.firebase.database.FirebaseDatabase
 */
public class Key_Static_Methods {
    public static boolean isConnected(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())){
            return true;
        }else {
            return false;
        }


    }

    /**
     * Saves income or expense data to shared preferences and updates the Firebase database.
     *
     * @param context the application context
     * @param status the type of data (income or expense)
     * @param amount the amount to be saved
     */
    public static void data_saved_to_SharedPreference(Context context, String status, int amount) {

        SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);

        if (status.equals(Key_Static.INCOME)) {
            int balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE, 0);
            int income = spp.getInt(Key_SharedPreference.TOTAL_INCOME, 0);

            spp.edit().putInt(Key_SharedPreference.TOTAL_INCOME, income + amount).apply();
            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance + amount).apply();
        } else if (status.equals(Key_Static.EXPENSE)) {
            int balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE, 0);
            int expense = spp.getInt(Key_SharedPreference.TOTAL_EXPENSE, 0);

            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance - amount).apply();
            spp.edit().putInt(Key_SharedPreference.TOTAL_EXPENSE, expense + amount).apply();
        }

        //Firebase sync
        Key_Static_Methods.update_done_to_firebase(context);
    }
    /**
     * Sends data to Firebase database.
     *
     * @param context the application context
     * @param update an instance of {@code Income_Expense_Balance_Update} containing updated financial data
     * @param loc_live an instance of {@code Location_Make} containing the current location
     */

    public static void sending_data_to_firebase(Context context, Income_Expense_Balance_Update update, Location_Make loc_live){

        SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        String account_name = spp.getString(Key_SharedPreference.ACCOUNT_NAME,"");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for(DataSnapshot snap:snapshot.getChildren()){

                        if(snap.child("email").getValue(String.class).equals(account_name)){
                            if(update != null){
                                snap.getRef().child("total_balance").setValue(update.balance);
                                snap.getRef().child("total_income").setValue(update.income);
                                snap.getRef().child("total_expense").setValue(update.expense);
                            }
                            if(loc_live != null){
                                snap.getRef().child("latitute").setValue(loc_live.latitute);
                                snap.getRef().child("longitute").setValue(loc_live.longitute);
                            }


                        }

                    }


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * Updates the shared preferences for income or expense modification and syncs with Firebase.
     *
     * @param context the application context
     * @param status the type of data (income or expense)
     * @param remove_amount the amount to be removed
     * @param amount the new amount to be added
     */
    public static void data_saved_to_SP_for_update_list(Context context, String status, int remove_amount, int amount) {

        SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);

        int balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE, 0);
        int income = spp.getInt(Key_SharedPreference.TOTAL_INCOME, 0);
        int expense = spp.getInt(Key_SharedPreference.TOTAL_EXPENSE, 0);

        if (status.equals(Key_Static.INCOME)) {

            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance - remove_amount).apply(); // SUB
            balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE, 0); // Get Latest Value
            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance + amount).apply(); //Applied


            spp.edit().putInt(Key_SharedPreference.TOTAL_INCOME, income - remove_amount).apply();
            income = spp.getInt(Key_SharedPreference.TOTAL_INCOME, 0);
            spp.edit().putInt(Key_SharedPreference.TOTAL_INCOME, income + amount).apply();
        } else if (status.equals(Key_Static.EXPENSE)) {

            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance + remove_amount).apply();
            balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE, 0);
            spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, balance - amount).apply();

            spp.edit().putInt(Key_SharedPreference.TOTAL_EXPENSE, expense - remove_amount).apply();
            expense = spp.getInt(Key_SharedPreference.TOTAL_EXPENSE, 0);
            ;
            spp.edit().putInt(Key_SharedPreference.TOTAL_EXPENSE, expense + amount).apply();

        }

        //Firebase sync
        Key_Static_Methods.update_done_to_firebase(context);
    }
    /**
     * Retrieves goal information from shared preferences.
     *
     * @param context the application context
     * @param key the key to retrieve the goal information
     * @return an {@code ArrayList<String>} containing category, progress, and limit
     */

    public static ArrayList<String> get_goal_information(Context context, String key) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);
            String category = spp.getString(key, key);
            Log.d("Goal_testing", " : "+category);
            arrayList.add(category);

            String progress = String.valueOf(spp.getInt("PROGRESS_" + key.toUpperCase(), 0));
            arrayList.add(progress);

            String limit = String.valueOf(spp.getInt("LIMIT_" + key.toUpperCase(), 1));
            arrayList.add(limit);


            Log.d("Goal_testing", "get_goal_information: " + category + " ; " + progress + " ; " + limit);


        } catch (NullPointerException e) {
            Log.d("Goal_testing", "NullPointerException: ");
            e.printStackTrace();
        }
        return arrayList;
    }
    /**
     * Displays a dialog notifying the user that a limit has been exceeded.
     *
     * @param context the application context
     */

    public static void limit_exiting_notice(Context context){

    Dialog dialog = new Dialog(context);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setContentView(R.layout.dialog_main_limit_exit);
    dialog.setCanceledOnTouchOutside(true);
    //Displaying Dialog
    dialog.show();

    Button btSubmit = dialog.findViewById(R.id.sending_feedback_btn_review_dialog);

    btSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, GoalRecyclerView.class));
        }
    });
    dialog.findViewById(R.id.do_not_send_feedback).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });

}
    /**
     * Creates and displays a custom notification.
     *
     * @param context the application context
     * @param title the title of the notification
     * @param body the body text of the notification
     */
    public static void creating_custom_notification(Context context, String title, String body){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, FCM_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.drawable.message)
                .setContentTitle(title)
                .setContentText(body)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.notifications_active))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                        .bigLargeIcon(null))
                .setColor(Color.BLUE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Updates the balance, income, and expense data in the Firebase database.
     *
     * @param context the application context
     */
    public static void update_done_to_firebase(Context context){
        SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        String balance = String.valueOf(spp.getInt(Key_SharedPreference.TOTAL_BALANCE,0));
        String income = String.valueOf(spp.getInt(Key_SharedPreference.TOTAL_INCOME,0));
        String expense = String.valueOf(spp.getInt(Key_SharedPreference.TOTAL_EXPENSE,0));

        Income_Expense_Balance_Update user = new Income_Expense_Balance_Update(balance,income,expense);
        sending_data_to_firebase(context,user,null);
    }

    public static void adjusting_all_balance(Context context){

        SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

        String acc_name = spp.getString(Key_SharedPreference.ACCOUNT_NAME,"");
        boolean open_state = spp.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN,true);

        SharedPreferences.Editor editor = spp.edit();
        editor.clear().apply();

        spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        spp.edit().putString(Key_SharedPreference.ACCOUNT_NAME,acc_name).apply();
        spp.edit().putBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN,open_state).apply();

        update_done_to_firebase(context);

        Sqlite_DatabaseSource sqliteDatabaseSource=new Sqlite_DatabaseSource(context);
        sqliteDatabaseSource.delete_all_records();

    }

}
