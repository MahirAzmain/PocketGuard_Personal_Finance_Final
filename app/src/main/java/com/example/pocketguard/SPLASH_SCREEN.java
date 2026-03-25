
package com.example.pocketguard;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketguard.Account.Login;

public class SPLASH_SCREEN extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;
    //variables
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;

    ProgressBar progressBar;
    TextView textView;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//
//        //Animations
//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim =AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//
//        //Hooks
//        image=findViewById(R.id.imageView);
//        logo=findViewById(R.id.textView);
//        slogan=findViewById(R.id.textView2);
//
//        image.setAnimation(topAnim);
//        logo.setAnimation(bottomAnim);
//        slogan.setAnimation(bottomAnim);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent =new Intent(SPLASH_SCREEN.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_SCREEN);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);

        if (sp.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true)) {
            setContentView(R.layout.activity_splash_screen);   //This is the FIRST time
            Log.d(TAG, "onCreate: Called now ins 1 : " + sp.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true));
        } else {
            setContentView(R.layout.activity_splash_screen);   //This is the every time               /// this is the setting the content view
            Log.d(TAG, "onCreate: Called now ins 2 : " + sp.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true));
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.text_view);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        no_internet_alert();
        one_time_show_AppOverview();

    }


    //this block is for one time APP_Toor_AppOverView
    private void one_time_show_AppOverview() {
        SharedPreferences sharedPreferences = getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);

        boolean bln = sharedPreferences.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true);   //ata store korba *on creat* koi bar run koracha

        if (bln == true) {
            progressAnimation_1st_time();
            //this portion will update the boolean value status , true to false
            Log.d(TAG, "onCreate: Called now ins 1st : " + sharedPreferences.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true));
        } else if (bln == false) {
            progressAnimation_other_time();
            Log.d(TAG, "onCreate: Called now ins other : " + sharedPreferences.getBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true));
        }
    }

    public void progressAnimation_1st_time() {

        Splash_Screen_ProgressAnimation_FirstTime anim = new Splash_Screen_ProgressAnimation_FirstTime(this, progressBar, textView, 0f, 100f);
        anim.setDuration(4000);
        progressBar.setAnimation(anim);

    }

    public void progressAnimation_other_time() {

        Splash_Screen_ProgressAnimation_OthersTime anim = new Splash_Screen_ProgressAnimation_OthersTime(this, progressBar, textView, 0f, 100f);
        anim.setDuration(400);
        progressBar.setAnimation(anim);

    }

    //this is for No internet alert
    public void no_internet_alert() {

        boolean val = Key_Static_Methods.isConnected(this);

        if (val == false) {
            //Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
            Toast.makeText(SPLASH_SCREEN.this, "প্লাবন : আগে ইন্টারনেট অন করুন", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }


    }

}
