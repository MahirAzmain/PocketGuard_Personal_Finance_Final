package com.example.pocketguard.Json;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketguard.R;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Activity class for displaying rank details.
 *
 * <p>This class demonstrates the use of the MVC (Model-View-Controller) design pattern. The activity
 * acts as the controller, fetching the rank data (model) passed via an intent and updating the UI view.</p>
 */
public class Rank extends AppCompatActivity {

    TextView textView;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        textView = findViewById(R.id.textView);
        // Fetching rank data from the intent and displaying it
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("rank"));
            // MVC pattern: Updating the view with rank data
           textView.append(

                    "#1 : "+jsonObject.getString("1") +"\n"+
                    "#2 : "+jsonObject.getString("2") +"\n"+
                    "#3 : "+jsonObject.getString("3") +"\n"+
                    "#4 : "+jsonObject.getString("4") +"\n"
            );

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}