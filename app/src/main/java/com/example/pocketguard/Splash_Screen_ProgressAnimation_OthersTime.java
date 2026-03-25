package com.example.pocketguard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pocketguard.Settings.LoginActivity;


public class Splash_Screen_ProgressAnimation_OthersTime extends Animation {

    private Context context ;
    private ProgressBar progressBar ;
    private TextView textView ;
    private  float from ;
    private float to ;

    public Splash_Screen_ProgressAnimation_OthersTime(Context context, ProgressBar progressBar, TextView textView, float from, float to) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value = from + (to - from) * interpolatedTime ;
        progressBar.setProgress((int) value);
        textView.setText((int)value+" %");

        if (value == to){

            SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,Context.MODE_PRIVATE);
            if(spp.getString(Key_SharedPreference.MY_PIN,"").equals("")){
                //No Pin Setted
                context.startActivity(new Intent(context, HomeActivity.class));
            }else {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("app_start","OPEN");

                context.startActivity(intent);
            }


        }
    }
}


