package com.example.pocketguard.Settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.HomeActivity;
import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText etPasswordLogin;
    String i = "";
    private ImageButton ivBtnEight;
    private ImageButton ivBtnFive;
    private ImageButton ivBtnFour;
    private ImageButton ivBtnNine;
    private ImageButton ivBtnOne;
    private ImageButton ivBtnSeven;
    private ImageButton ivBtnSix;
    private ImageButton ivBtnThree;
    private ImageButton ivBtnTwo;
    private ImageButton ivBtnZero;
    int j = 0;
    private TextView tvForgot;
    private TextView tvReset;

    public LoginActivity() {
        new ArrayList();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_login_for_pin);
        findViewById();
    }

    private void findViewById() {
        this.ivBtnOne = (ImageButton) findViewById(R.id.btn1);
        this.ivBtnTwo = (ImageButton) findViewById(R.id.btn2);
        this.ivBtnThree = (ImageButton) findViewById(R.id.btn3);
        this.ivBtnFour = (ImageButton) findViewById(R.id.btn4);
        this.ivBtnFive = (ImageButton) findViewById(R.id.btn5);
        this.ivBtnSix = (ImageButton) findViewById(R.id.btn6);
        this.ivBtnSeven = (ImageButton) findViewById(R.id.btn7);
        this.ivBtnEight = (ImageButton) findViewById(R.id.btn8);
        this.ivBtnNine = (ImageButton) findViewById(R.id.btn9);
        this.ivBtnZero = (ImageButton) findViewById(R.id.btn0);
        this.ivBtnOne.setOnClickListener(this);
        this.ivBtnTwo.setOnClickListener(this);
        this.ivBtnThree.setOnClickListener(this);
        this.ivBtnFour.setOnClickListener(this);
        this.ivBtnFive.setOnClickListener(this);
        this.ivBtnSix.setOnClickListener(this);
        this.ivBtnSeven.setOnClickListener(this);
        this.ivBtnEight.setOnClickListener(this);
        this.ivBtnNine.setOnClickListener(this);
        this.ivBtnZero.setOnClickListener(this);
        EditText editText = (EditText) findViewById(R.id.et_password);
        this.etPasswordLogin = editText;
        editText.addTextChangedListener(this);

        TextView textView2 = (TextView) findViewById(R.id.tv_reset);
        this.tvReset = textView2;
        textView2.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.tv_reset) {

            if (id == R.id.btn0) {
                this.i += 0;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn1) {
                this.i += 1;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn2) {
                this.i += 2;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn3) {
                this.i += 3;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn4) {
                this.i += 4;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn5) {
                this.i += 5;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn6) {
                this.i += 6;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn7) {
                this.i += 7;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn8) {
                this.i += 8;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else if (id == R.id.btn9) {
                this.i += 9;
                this.etPasswordLogin.setText("");
                this.etPasswordLogin.setText(this.i);
                return;
            } else {

                return;
            }

        } else {
            this.i = "";
            this.etPasswordLogin.setText("");
        }

        if(id == R.id.tv_reset){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
        int length = this.etPasswordLogin.length();
        this.j = length;
        if (length == 4) {
            SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

            if (spp.getString(Key_SharedPreference.MY_PIN,"").equals(this.etPasswordLogin.getText().toString())) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return;
            }
            this.etPasswordLogin.setText("");
            this.i = "";
            ErrorDialog(this,  getString(R.string.wrong_pass), getString(R.string.wrong_pass_info));
        }
    }

    public void ErrorDialog(Context context, String s1, String s2) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        Dialog dialog = builder.create();


        TextView sub_title = view.findViewById(R.id.sub_title);
        TextView main_title = view.findViewById(R.id.main_title);

        TextView btn_ok = view.findViewById(R.id.btn_ok);

        main_title.setText(s1);
        sub_title.setText(s2);

        dialog.show();


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setCancelable(true);

        dialog.show();
    }

}
