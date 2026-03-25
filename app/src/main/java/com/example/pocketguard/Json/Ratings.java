package com.example.pocketguard.Json;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketguard.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Activity class for displaying ratings.
 *
 * <p>This class demonstrates the use of the MVC (Model-View-Controller) design pattern. It acts as the controller,
 * fetching the ratings data (model) passed via an intent and updating the UI view.</p>
 */
public class Ratings extends AppCompatActivity {

    ListView listView;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        listView = findViewById(R.id.list_play);

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("ratings"));
            ArrayList<String> pl = new ArrayList<>();

/*            String club = getIntent().getStringExtra("app_name");

            ImageView imageView = findViewById(R.id.image);

            if(club.equals("Manchester United")){
                  imageView.setImageResource(R.drawable.manchester_united);
            }else if(club.equals("FC Barcelona")){
                imageView.setImageResource(R.drawable.fc_barcelona);
            }else if(club.equals("Bayern Munich")){
                imageView.setImageResource(R.drawable.bayern);
            }else if(club.equals("Juventus")){
                imageView.setImageResource(R.drawable.juventus);
            }else if(club.equals("Paris Saint-Germain")){
                imageView.setImageResource(R.drawable.paris_saint);
            }else if(club.equals("Real Madrid")){
                imageView.setImageResource(R.drawable.real_madrid);
            }else {
                imageView.setVisibility(View.GONE);
            }*/

            // Extracting rating states from JSON array
            for(int i=0; i<jsonArray.length(); i++){

                pl.add(jsonArray.getJSONObject(i).getString("state")) ;

            }

            // Populating ListView with rating states
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,pl);
            listView.setAdapter(arrayAdapter);


            // Setting item click listener to view rate details
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(pos);
                        Intent intent = new Intent(Ratings.this,Rate_View.class);
                        intent.putExtra("rate",jsonObject.toString());
                        startActivity(intent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}