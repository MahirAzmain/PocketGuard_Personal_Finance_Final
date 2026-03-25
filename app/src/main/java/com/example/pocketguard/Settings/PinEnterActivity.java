package com.example.pocketguard.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;


public class PinEnterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText etPassword;
    String h = "";
    int i = 0;
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
    private TextView tvReset;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_pinenter);
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
        this.etPassword = editText;
        editText.addTextChangedListener(this);
        TextView textView = (TextView) findViewById(R.id.tv_reset);
        this.tvReset = textView;
        textView.setOnClickListener(this);
        getIntent().getStringExtra("Home");
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.tv_reset) {

            if (id == R.id.btn0) {
                this.h += 0;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn1) {
                this.h += 1;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn2) {
                this.h += 2;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn3) {
                this.h += 3;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn4) {
                this.h += 4;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn5) {
                this.h += 5;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn6) {
                this.h += 6;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn7) {
                this.h += 7;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn8) {
                this.h += 8;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else if (id == R.id.btn9) {
                this.h += 9;
                this.etPassword.setText("");
                this.etPassword.setText(this.h);
                return;
            } else {

                return;
            }

        } else {
            this.etPassword.setText("");
        }
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
        int length = this.etPassword.length();
        this.i = length;
        if (length == 4) {

            SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
            spp.edit().putString(Key_SharedPreference.MY_PIN,etPassword.getText().toString()).apply();

            Intent intent = new Intent(this, PinConfirm.class);
            intent.putExtra("pin", this.etPassword.getText().toString());
            intent.putExtra("Home", "Home");
            startActivity(intent);
            finish();
        }
    }
}
