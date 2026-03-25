package com.example.pocketguard.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * The Register class handles user registration, update, and deletion functionalities.
 * It follows the Model-View-Controller (MVC) design pattern.
 *
 * <p>The class includes:
 * <ul>
 *   <li>View elements for user input such as EditText for email, password, name, date of birth, and security question</li>
 *   <li>Controller logic to handle user registration, update, and deletion</li>
 *   <li>Model interaction with the Users data from Firebase</li>
 * </ul>
 */
public class Register extends AppCompatActivity { 

    public EditText et_email,et_pass,et_name,et_dob,et_security;
    public String email,pass,name,dob,security;
    public TextView already_register,register_text;
    public Button register,update,delete;

    public SharedPreferences spp;
    public DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize SharedPreferences
        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        // Initialize view elements

        et_email = findViewById(R.id.email);
        et_pass = findViewById(R.id.password);
        et_name = findViewById(R.id.name);
        et_dob = findViewById(R.id.dob);
        et_security = findViewById(R.id.security);

        register = findViewById(R.id.register);
        already_register = findViewById(R.id.already_register);
        register_text = findViewById(R.id.register_text);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        // Check if the user wants to update their details
        String str = getIntent().getStringExtra("update");
        if(str != null && str.equals("2007111")){
            fetchData();

            register.setVisibility(View.GONE);
            already_register.setVisibility(View.GONE);
            register_text.setText("Update Or Delete");
        }else {
            update.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        // Controller logic for registration
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_email.getText().toString().equals("") && !et_pass.getText().toString().equals("") && !et_name.getText().toString().equals("") && !et_dob.getText().toString().equals("")  && !et_security.getText().toString().equals("")){

                    if(Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
                        email = et_email.getText().toString();
                        pass = et_pass.getText().toString();
                        name = et_name.getText().toString();
                        dob = et_dob.getText().toString();
                        security = et_security.getText().toString();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        Users users = new Users(email,pass,name,dob,security,"","","","","");
                        databaseReference.push().setValue(users);

                        Toast.makeText(Register.this, "Register Done ✅", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this,Login.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Register.this, "Incorrect Email Format", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Register.this, "Fill All Data Correctly !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // Controller logic for navigation to login
        already_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        // Controller logic for updating user details
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            for(DataSnapshot snap : snapshot.getChildren()){
                                Users user = snap.getValue(Users.class);
                                if(user != null && user.email.equals(spp.getString(Key_SharedPreference.ACCOUNT_NAME,""))){

                                    if(!et_email.getText().toString().equals(spp.getString(Key_SharedPreference.ACCOUNT_NAME,""))){
                                        Toast.makeText(Register.this, "Email Can't be Edited !!", Toast.LENGTH_LONG).show();
                                    }else {
                                        snap.getRef().child("pass").setValue(et_pass.getText().toString());
                                        snap.getRef().child("name").setValue(et_name.getText().toString());
                                        snap.getRef().child("dob").setValue(et_dob.getText().toString());
                                        snap.getRef().child("security").setValue(et_security.getText().toString());
                                        Toast.makeText(Register.this, "Updated", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                        } catch (NullPointerException e){

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        // Controller logic for deleting user account
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Users user = snap.getValue(Users.class);

                                if(user != null && user.email.equals(spp.getString(Key_SharedPreference.ACCOUNT_NAME,""))){

                                    snap.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Register.this, "Account Deleted !!", Toast.LENGTH_SHORT).show();

                                            spp.edit().putBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, true).apply();//First Time App Open
                                            Intent intent = new Intent(Register.this,Login.class);
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Failed !!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }catch (NullPointerException e){

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    /**
     * Fetches the user data from Firebase and populates the input fields.
     */

    public void fetchData(){
        String account = spp.getString(Key_SharedPreference.ACCOUNT_NAME,"");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    for(DataSnapshot snap : snapshot.getChildren()){
                        Users users = snap.getValue(Users.class);
                        if(users !=null && users.email.equals(account)){

                            et_email.setText(users.email);
                            et_pass.setText(users.pass);
                            et_name.setText(users.name);
                            et_dob.setText(users.dob);
                            et_security.setText(users.security);
                            return;
                        }
                    }

                } catch (NullPointerException e){

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}