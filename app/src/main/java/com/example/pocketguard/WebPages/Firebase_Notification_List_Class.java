package com.example.pocketguard.WebPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.Bookmarks.Sqlite_StudentListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.pocketguard.R;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Activity class for displaying Firebase notifications list.
 *
 * <p>This class demonstrates the use of the MVC (Model-View-Controller) and Observer design patterns.
 * It acts as the controller, managing the Firebase database reference and updating the UI with notifications.</p>
 */
public class Firebase_Notification_List_Class extends AppCompatActivity {

    boolean bool = false;
    ListView listView;
    DatabaseReference databaseReference;
    FloatingActionButton floatingActionButton_home;

    String string_notification_link;

    private ArrayList<Firebase_Notifications_List> feedbacks;
    private Firebase_Notifications_Adapter_List customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_notifications_activity_list);

        bool = true;

        home_button_caller();

        //Here Id loading is not necessary because it comes from previous activity
        SharedPreferences sp = getSharedPreferences("KEY", Context.MODE_PRIVATE);


        // Initializing views and variables
        listView = findViewById(R.id.listView_firebase_notification_list);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Recommendation");

        feedbacks = new ArrayList<>();
        customAdapter = new Firebase_Notifications_Adapter_List(Firebase_Notification_List_Class.this, feedbacks);


        // Setting up click listener for notification item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Getting the link of the notification
                Firebase_Notifications_List feedback_members = feedbacks.get(position);
                string_notification_link = feedback_members.getLink().toString();

                Toast.makeText(Firebase_Notification_List_Class.this, "Loading....", Toast.LENGTH_SHORT).show();
                 // Opening the notification link in a webview
                Intent intent = new Intent(Firebase_Notification_List_Class.this,All_Web_Pages_NoAds.class);
                if (string_notification_link != null) {
                    intent.putExtra("Web", string_notification_link);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        // Listening for changes in Firebase database
        databaseReference.limitToLast(12).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Handling data changes in Firebase database
                feedbacks.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    try {
                        Firebase_Notifications_List fd = dataSnapshot.getValue(Firebase_Notifications_List.class);
                        feedbacks.add(fd);
                    } catch (NullPointerException e){ //VVI
                        e.printStackTrace();
                        break;
                    }catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }

                }
                Collections.reverse(feedbacks);
                listView.setAdapter(customAdapter);


                //Update the notification count view
                if (snapshot.exists())
                {
                    try {

                        int counter = (int)snapshot.getChildrenCount();

                        if(bool == true)
                        {
                            bool = false;
                        }

                    } catch (NullPointerException e){  //VVI
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
           // Handling errors
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Firebase_Notification_List_Class.this, "Sorry something going wrong", Toast.LENGTH_SHORT).show();
            }
        });
        super.onStart();
    }

    /**
     * Handles click on the home button.
     * Navigates to the home activity (Sqlite_StudentListActivity).
     */

    public void home_button_caller(){
        floatingActionButton_home =  findViewById(R.id.bottom_nav_home_bookmark);
        floatingActionButton_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home = new Intent(Firebase_Notification_List_Class.this, Sqlite_StudentListActivity.class);
                startActivity(intent_home);
            }
        });

    }
}