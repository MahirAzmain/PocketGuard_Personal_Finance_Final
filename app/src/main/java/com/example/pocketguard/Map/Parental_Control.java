package com.example.pocketguard.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.Account.Users;
import com.example.pocketguard.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Activity for parental control functionalities.
 * This activity allows parents to view and locate their child's current location.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Model-View-Controller (MVC):</b> This class serves as the controller, managing interactions between the model (user data) and the view (UI elements).</li>
 *     <li><b>Observer:</b> The ValueEventListener interface is implemented to observe changes in the database and update the UI accordingly.</li>
 * </ul>
 */
public class Parental_Control extends AppCompatActivity {

    EditText et1,et2;
    TextView tv1,tv2,tv3,tv4,tv5;
    Double lati=null,longi=null;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental_control);

        et1 = findViewById(R.id.child_mail);
        et2 = findViewById(R.id.child_security);

        tv1 = findViewById(R.id.child_lat);
        tv2 = findViewById(R.id.child_long);
        tv3 = findViewById(R.id.total_balance);
        tv4 = findViewById(R.id.total_income);
        tv5 = findViewById(R.id.total_expense);
        // Hide TextViews initially
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);

    }

    /**
     * Retrieves the child's location data from the Firebase Realtime Database based on entered email and security code.
     * Shows the location and other details if the child is found.
     * Displays a toast message if the child is not found or if fields are empty.
     *
     * @param view The View object that triggered this method (Button).
     */
    public void get_location(View view) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    if(!et1.getText().toString().equals("") && !et2.getText().toString().equals("")){
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Users users = snap.getValue(Users.class);

                            if(users!=null && users.email.equals(et1.getText().toString()) && users.security.equals(et2.getText().toString())){
                                // Update TextViews with child's data
                                tv1.setText("Latitude\n"+users.latitute);
                                tv2.setText("longitude\n"+users.longitute);
                                tv3.setText("Total Balance : "+users.total_balance);
                                tv4.setText("Total Income : "+users.total_income);
                                tv5.setText("Total Expense : "+users.total_expense);
                                // Show TextViews
                                tv1.setVisibility(View.VISIBLE);
                                tv2.setVisibility(View.VISIBLE);
                                tv3.setVisibility(View.VISIBLE);
                                tv4.setVisibility(View.VISIBLE);
                                tv5.setVisibility(View.VISIBLE);

                                // Store latitude and longitude
                                lati = Double.valueOf(users.latitute);
                                longi = Double.valueOf(users.longitute);
                                flag = true;
                                Log.d("Tesssssss", "Parental: "+lati+","+longi);
                            }
                        }

                        if(flag == false){
                            Toast.makeText(Parental_Control.this, "Child Not Found !!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Parental_Control.this, "Please, Fill All Fields !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (NullPointerException e){
                       //handle null pointer exception
                }catch (Exception e) {
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
     * Shows the child's location on the map if available.
     * Displays a toast message if the location is not available.
     *
     * @param view The View object that triggered this method (Button).
     */
    public void show_in_map(View view) {

        if(lati != null & longi != null){
            Intent intent = new Intent(this, Maps_Activity.class);
            intent.putExtra("LAT",lati);
            intent.putExtra("LON",longi);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Child Not Selected !!", Toast.LENGTH_SHORT).show();
        }
    }
}