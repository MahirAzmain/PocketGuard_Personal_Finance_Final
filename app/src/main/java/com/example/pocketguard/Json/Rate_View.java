package com.example.pocketguard.Json;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketguard.R;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Activity class for displaying rate details.
 *
 * <p>This class follows the MVC (Model-View-Controller) design pattern. It acts as the controller,
 * fetching the rate data (model) passed via an intent and updating the UI view.</p>
 */

public class Rate_View extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_view);

        TextView textView;

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("rate"));
            textView = findViewById(R.id.textView);
            // MVC pattern: Updating the view with rate data
            textView.append(
                            "State : "+jsonObject.getString("state") + "\n" +
                            "Numbers : "+jsonObject.getString("number") + "\n" +
                            "Color State : "+jsonObject.getString("color") + "\n"
            );


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}