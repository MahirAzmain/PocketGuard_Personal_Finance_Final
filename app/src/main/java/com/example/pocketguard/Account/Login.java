package com.example.pocketguard.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.HomeActivity;
import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * The Login class handles the login functionality of the application.
 * It follows the Model-View-Controller (MVC) design pattern.
 *
 * <p>The class includes:
 * <ul>
 *   <li>View elements such as EditText for email and password, Button for login, and TextView for register</li>
 *   <li>Controller logic to handle login action and interact with the Firebase database</li>
 *   <li>Model interaction with the Users data from Firebase</li>
 * </ul>
 */
public class Login extends AppCompatActivity {

    /** EditText for entering password. */
  
    /** EditText for entering email. */
    public EditText et_email, et_pass;

    /** Button to trigger the login process. */
    Button login;

    /** TextView to navigate to the registration page. */
    TextView register;
    /** Flag to indicate if the password is incorrect. */
    public boolean flag_wrong_pass = false;
    public DatabaseReference databaseReference;
    /**
     * Called when the activity is starting. Initializes the activity, sets the content view,
     * and sets up click listeners for login and register actions.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.email_login_page_et);
        et_pass = findViewById(R.id.pass_login_page_et);

        login = findViewById(R.id.login);
        register = findViewById(R.id.creat_account);


        // Initialize databaseReference here
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 code_for_login();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
    /**
     * Handles the login functionality. It checks the credentials against the data in Firebase,
     * and if successful, starts the HomeActivity and updates SharedPreferences with user data.
     */

    public void code_for_login() {
        // Check if EditText fields are null
        if (et_email == null || et_pass == null) {
            Log.e("LoginActivity", "EditText fields are null");
            // Handle the case where EditText fields are not found
            return;
        }

        Log.d("For_login_error", "code_for_login: ");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Log.d("For_login_error", "code_for_login: Condition False");
                    if (!et_email.getText().toString().equals("") && !et_pass.getText().toString().equals("")) {
                        Log.d("For_login_error", "code_for_login: Condition True");
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Log.d("For_login_error", "code_for_login: SnapShot exists");
                            Users users = snap.getValue(Users.class);
                            if (users != null && et_email.getText().toString().equals(users.email) && et_pass.getText().toString().equals(users.pass)) {
                                flag_wrong_pass = true;
                                Toast.makeText(Login.this, "Login Successful ✅", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                startActivity(intent);

                                SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
                                spp.edit().remove(Key_SharedPreference.TOTAL_BALANCE).apply();
                                spp.edit().remove(Key_SharedPreference.TOTAL_INCOME).apply();
                                spp.edit().remove(Key_SharedPreference.TOTAL_EXPENSE).apply();

                                Log.d("For_login_error", "code_for_login: SnapShot Matcheced 1");
                                spp.edit().putString(Key_SharedPreference.ACCOUNT_NAME,et_email.getText().toString()).apply();

                                spp.edit().putBoolean(Key_SharedPreference.FIRST_TIME_APP_OPEN_SPLASH_SCREEN, false).apply();//First Time App Open
                                Log.d("For_login_error", "code_for_login: SnapShot Matcheced 2");
                                //Setting income expense and balance
                                spp.edit().putInt(Key_SharedPreference.TOTAL_BALANCE, Integer.parseInt(users.total_balance)).apply();
                                spp.edit().putInt(Key_SharedPreference.TOTAL_INCOME, Integer.parseInt(users.total_income)).apply();
                                spp.edit().putInt(Key_SharedPreference.TOTAL_EXPENSE, Integer.parseInt(users.total_expense)).apply();
                                Log.d("For_login_error", "code_for_login: SnapShot Matcheced 3");

                            }
                        }
                        if(flag_wrong_pass==false){
                            Toast.makeText(Login.this, "Wrong Email or Password !!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Fill All Data Correctly !!", Toast.LENGTH_SHORT).show();
                    }

                } catch (NullPointerException e) {
                    // Handle null pointer exception
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
    /**
     * Called when the activity has detected the user's press of the back key.
     *
     * <p>Default implementation of onBackPressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}