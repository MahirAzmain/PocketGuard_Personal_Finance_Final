package com.example.pocketguard.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;

public class Settings extends AppCompatActivity {

    public Switch aSwitchPassword;
    SharedPreferences spp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

        this.aSwitchPassword = (Switch) findViewById(R.id.switch_password);
        checking_button_state();

        this.aSwitchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    startActivity(new Intent(Settings.this, PinEnterActivity.class));
                    return;
                }else {
                    Toast.makeText(Settings.this, "Password Removed !!", Toast.LENGTH_SHORT).show();
                    spp.edit().remove(Key_SharedPreference.MY_PIN).apply();
                }
            }
        });


    }

    public void checking_button_state(){

        if(spp.getString(Key_SharedPreference.MY_PIN,"").equals("")){
            aSwitchPassword.setChecked(false);
        }else {
            aSwitchPassword.setChecked(true);
        }

    }

    public void onSelectItem(View view) {
        // Handle onClick event here
    }

}