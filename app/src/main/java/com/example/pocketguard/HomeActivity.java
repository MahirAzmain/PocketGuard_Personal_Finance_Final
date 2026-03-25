package com.example.pocketguard;

import static android.content.ContentValues.TAG;

import static com.example.pocketguard.Key_SharedPreference.FCM_NOTIFICATION_LINK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pocketguard.Account.Login;
import com.example.pocketguard.Account.Register;
import com.example.pocketguard.Account.ShowImage;
import com.example.pocketguard.GoalSet.GoalRecyclerView;
import com.example.pocketguard.Json.MainActivity_Json;
import com.example.pocketguard.Map.For_Map_Static_Functions;

import com.example.pocketguard.Map.Parental_Control;
import com.example.pocketguard.Settings.Settings;
import com.example.pocketguard.ShowData.Sqlite_ListActivity;
import com.example.pocketguard.WebPages.Firebase_Notification_List_Class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
/**
 * The {@code HomeActivity} class represents the main activity of the PocketGuard application.
 *
 * <p>This activity serves as the home screen of the application, displaying the user's balance, income, and expenses.
 * It also provides navigation options to various features of the application.</p>
 *
 * <p>This class demonstrates several design patterns, including:</p>
 * <ul>
 *   <li>Singleton Pattern: Utilized for SharedPreferences access.</li>
 *   <li>Observer Pattern: Implemented for navigation items selection in the drawer.</li>
 *   <li>Template Method Pattern: Used for setting up the activity's UI components and behavior.</li>
 *   <li>Factory Method Pattern: Employed for creating alert dialog instances.</li>
 * </ul>
 */


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvUserEmail,tvUserName;
    // SharedPreferences instance for accessing application preferences
    SharedPreferences spp;
    private TextView balance_tv,income_tv,expense_tv;
    CardView income_cardview,expense_cardview;
    LinearLayout balance_linearlayout;


    //Recomnded websites
    boolean bool = false;
    ListView listView;
    // Floating action button for adding transactions
    FloatingActionButton floatingActionButton_home;

    String string_notification_link;
    // Navigation items
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Initialize location services for the map
        For_Map_Static_Functions.location_initialize(this);
        // Extract notification link if received via intent

        if (getIntent() != null && getIntent().hasExtra("key_link_fcm")) {
            //Log.d("Firebase_Push_Setting", "onCreate: NOT NULL");
           Key_Static.ONESIGNAL_LINK = getIntent().getExtras().getString("key_link_fcm");  //Flow will occur like onesignal
           Log.d("Firebase_Push_Setting", "onCreate: Key: " + "key_link_fcm" + "Data: " + getIntent().getExtras().getString("key_link_fcm"));
        }
        // Access SharedPreferences singleton instance
        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setup recommended websites button
        recomnded_websites_button(); 
        show_image(); 
      
        // Setup navigation drawer 
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));


        // Setup bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.option_add){
                    startActivity(new Intent(HomeActivity.this, AddExpenceIncomeActivity.class));
                }

                return false;
            }
        });
        // Setup UI elements for balance, income, and expense
        balance_linearlayout = findViewById(R.id.balance_linearlayout);
        income_cardview = findViewById(R.id.income_cardview);
        expense_cardview = findViewById(R.id.expense_cardview);

        // Display balance, income, and expense
        set_income_expense();
        // Set click listeners for transaction views

        total_transaction();
        income_transaction();
        expense_transaction();
        message_taken_from_push();
        //testing_my_goal_perfectly_working_or_not();


        View headerView = navigationView.getHeaderView(0);
        this.tvUserEmail = (TextView) headerView.findViewById(R.id.tv_useremail);
        this.tvUserName = (TextView) headerView.findViewById(R.id.tv_username);
        this.tvUserEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Email Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void show_image() {
        LinearLayout linearLayout = findViewById(R.id.sites_linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, Firebase_Notification_List_Class.class));
            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    private void message_taken_from_push() {
        SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        String values = spp.getString(Key_SharedPreference.FCM_NOTIFICATION_LINK,"");

        if(values != ""){
            Toast.makeText(this, "MSG : "+values, Toast.LENGTH_LONG).show();
        }
        spp.edit().putString(Key_SharedPreference.FCM_NOTIFICATION_LINK,"").apply();
    }

    @Override
    protected void onPostResume() {

//        if (getIntent() != null && getIntent().hasExtra("key_link_fcm")) {
//
//            Key_Static.ONESIGNAL_LINK = getIntent().getExtras().getString("key_link_fcm");  //Flow will occur like onesignal
//            Log.d("Firebase_Push_Setting", "onCreate: Key: " + "key_link_fcm" + "Data: " + getIntent().getExtras().getString("key_link_fcm"));
//        }
        super.onPostResume();
    }

    public void recomnded_websites_button(){
        LinearLayout linearLayout = findViewById(R.id.show_all_photo_layout_outer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ShowImage.class));
            }
        });
    }

    public void total_transaction(){
        balance_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key_Static.BALANCE_LIST = true; // Very very important
                startActivity(new Intent(HomeActivity.this, Sqlite_ListActivity.class));
            }
        });
    }

    public void income_transaction(){
        income_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key_Static.INCOME_LIST = true; // Very very important
                startActivity(new Intent(HomeActivity.this, Sqlite_ListActivity.class));
            }
        });
    }

    public void expense_transaction(){
        expense_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key_Static.EXPENSE_LIST = true; // Very very important
                startActivity(new Intent(HomeActivity.this, Sqlite_ListActivity.class));
            }
        });
    }

    public void set_income_expense(){
        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

        balance_tv = findViewById(R.id.balance_tv);
        income_tv = findViewById(R.id.income_tv);
        expense_tv = findViewById(R.id.expense_tv);

        int balance = spp.getInt(Key_SharedPreference.TOTAL_BALANCE,0);
        int income = spp.getInt(Key_SharedPreference.TOTAL_INCOME,0);
        int expense = spp.getInt(Key_SharedPreference.TOTAL_EXPENSE,0);

        balance_tv.setText(String.valueOf(balance));
        income_tv.setText(String.valueOf(income));
        expense_tv.setText(String.valueOf(expense));

        //Firebase sync
        Key_Static_Methods.update_done_to_firebase(this);
    }


    @Override
    public void onBackPressed() {
        alert_dialog_all_time_in_back();
    }
    // Alear Dialog for all time on back press
    public void alert_dialog_all_time_in_back() {

        View alertCustomDialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_for_backpress,null);
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);

        alertDialog.setView(alertCustomDialog);
        Button no_btn = (Button) alertCustomDialog.findViewById(R.id.no_btn_backpress);
        no_btn.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.night_btn_backpress_no));
        Button yes_btn = (Button) alertCustomDialog.findViewById(R.id.yes_btn_backpress);
        yes_btn.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.night_btn_backpress_yes));

        final AlertDialog dialog = alertDialog.create();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
            }
        });
    }


    @Override
    protected void onDestroy() {
        Log.d("Checking", "onDestroy: 3");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("Checking", "onPause: ");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d("Checking", "onRestart: ");
        set_income_expense();   // Refreshing Every Time
        super.onRestart();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

            if (itemId == R.id.all_transaction) {
                Key_Static.BALANCE_LIST = true; // Very very important
                startActivity(new Intent(this, Sqlite_ListActivity.class));
            }else if (itemId == R.id.adjust_balance) {

                Key_Static_Methods.adjusting_all_balance(HomeActivity.this);

                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);

                Toast.makeText(this, "All Data Is Set To 0", Toast.LENGTH_SHORT).show();
            }else if (itemId == R.id.set_goal) {
                startActivity(new Intent(this, GoalRecyclerView.class));
            }else if (itemId == R.id.parental_control) {
                startActivity(new Intent(this, Parental_Control.class));
            }else if (itemId == R.id.action_settings) {
                startActivity(new Intent(this, Settings.class));
            }else if (itemId == R.id.log_out) {

                SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = spp.edit();
                editor.clear().apply();

                spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

                spp.edit().putBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true).apply();//First Time App Open
                startActivity(new Intent(this, Login.class));
            }else if (itemId == R.id.account_edit) {
                Intent intent = new Intent(this, Register.class);
                intent.putExtra("update","2007111");
                startActivity(intent);
            }else if (itemId == R.id.take_dec_ai) {
                Intent intent = new Intent(this, Gpt.class);
                startActivity(intent);
            }
            ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
            return true;

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.top_app) {
            startActivity(new Intent(this, MainActivity_Json.class));
        }else if(menuItem.getItemId() == R.id.set_goal){
            startActivity(new Intent(this, GoalRecyclerView.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }
}