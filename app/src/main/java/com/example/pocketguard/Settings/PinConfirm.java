package com.example.pocketguard.Settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.R;


public class PinConfirm extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText etConfirmPassword;
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

    String check_from_starting_app;
    boolean flag_check_start;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_pinconfirm);
        findViewById();

/*        check_from_starting_app = getIntent().getStringExtra("app_start");
        if(check_from_starting_app.equals("OPEN")){
            flag_check_start = true;
        }*/

        Log.d("pin_conform", "onCreate: ");
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
        this.etConfirmPassword = editText;
        editText.addTextChangedListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn0) {
            this.h += 0;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn1) {
            this.h += 1;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn2) {
            this.h += 2;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn3) {
            this.h += 3;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn4) {
            this.h += 4;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn5) {
            this.h += 5;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn6) {
            this.h += 6;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn7) {
            this.h += 7;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn8) {
            this.h += 8;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else if (id == R.id.btn9) {
            this.h += 9;
            this.etConfirmPassword.setText("");
            this.etConfirmPassword.setText(this.h);
            return;
        } else {
            return;
        }


    }

    public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
        int length = this.etConfirmPassword.length();
        this.i = length;

        Log.d("pin_conform", "onTextChanged: ");

        if (length == 4) {
            if (getIntent().getStringExtra("pin").equals(this.etConfirmPassword.getText().toString())) {
                Log.d("pin_conform", "get data with pin: "+etConfirmPassword.getText().toString());
                Toast.makeText(this, "Done !! Your Pin is : "+etConfirmPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                this.finish();
                return;
            }else if(true){


            }

/*            else if(flag_check_start){
                SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

                if(spp.getString(Key_SharedPreference.MY_PIN,"").equals(etConfirmPassword.getText().toString())){
                    //Matched
                    Toast.makeText(this, "Pin Matched !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PinConfirm.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(PinConfirm.this,PinConfirm.class);
                    startActivity(intent);
                }

            }*/
            Not_matched_Dialog(PinConfirm.this, getString(R.string.pass_notmatch), getString(R.string.pass_notmatch_info));
        }
    }

    public void Not_matched_Dialog(Activity context, String s1, String s2) {

        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        Dialog dialog = builder.create();


        TextView sub_title = view.findViewById(R.id.sub_title);
        TextView main_title = view.findViewById(R.id.main_title);

        TextView btn_ok = view.findViewById(R.id.btn_ok);
        dialog.setCanceledOnTouchOutside(false);
        main_title.setText(s1);
        sub_title.setText(s2);

        dialog.show();


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PinEnterActivity.class));
                context.finish();

            }
        });
        dialog.setCancelable(true);

        dialog.show();


    }
}
